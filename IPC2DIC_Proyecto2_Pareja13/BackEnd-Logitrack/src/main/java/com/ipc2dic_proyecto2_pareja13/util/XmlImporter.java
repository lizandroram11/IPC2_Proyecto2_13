package com.ipc2dic_proyecto2_pareja13.util;

import com.ipc2dic_proyecto2_pareja13.model.*;
import com.ipc2dic_proyecto2_pareja13.repository.*;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class XmlImporter {

    public static void importarDatos(String rutaArchivo,
                                     CentroRepository centroRepo,
                                     RutaRepository rutaRepo,
                                     MensajeroRepository mensajeroRepo,
                                     PaqueteRepository paqueteRepo,
                                     SolicitudRepository solicitudRepo) {
        try {
            File archivo = new File(rutaArchivo);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(archivo);
            doc.getDocumentElement().normalize();

            // Detectar raíz del enunciado (<logitrack><configuracion>...) o formato plano
            Element root = doc.getDocumentElement();
            Element config = root.getTagName().equalsIgnoreCase("logitrack")
                    ? (Element) root.getElementsByTagName("configuracion").item(0)
                    : root;

            // --- Centros ---
            NodeList centros = getNodes(config, "centros", "centro", "Centros", "Centro");
            for (int i = 0; i < centros.getLength(); i++) {
                Element e = (Element) centros.item(i);
                // Enunciado: elementos hijos; tu formato: atributos
                String id = getAttrOrChild(e, "id");
                String nombre = getAttrOrChild(e, "nombre");
                String ciudad = getAttrOrChild(e, "ciudad");
                int capacidad = Integer.parseInt(getAttrOrChild(e, "capacidad"));

                Centro centro = new Centro(id, nombre, ciudad, capacidad);
                centroRepo.guardar(centro);
            }

            // --- Rutas ---
            NodeList rutas = getNodes(config, "rutas", "ruta", "Rutas", "Ruta");
            for (int i = 0; i < rutas.getLength(); i++) {
                Element e = (Element) rutas.item(i);
                String id = getAttrOrChild(e, "id");
                String origen = getAttrOrChild(e, "origen");
                String destino = getAttrOrChild(e, "destino");
                double distancia = Double.parseDouble(getAttrOrChild(e, "distancia"));

                Ruta ruta = new Ruta(id, origen, destino, distancia);
                rutaRepo.guardar(ruta);
            }

            // --- Mensajeros ---
            NodeList mensajeros = getNodes(config, "mensajeros", "mensajero", "Mensajeros", "Mensajero");
            for (int i = 0; i < mensajeros.getLength(); i++) {
                Element e = (Element) mensajeros.item(i);
                String id = getAttrOrChild(e, "id");
                String nombre = getAttrOrChild(e, "nombre");
                int capacidad = Integer.parseInt(getAttrOrChild(e, "capacidad"));
                // Enunciado usa "centro"; tu formato usa "centroActual"
                String centroActual = getAttrOrChild(e, "centroActual");
                if (centroActual == null || centroActual.isEmpty()) {
                    centroActual = getAttrOrChild(e, "centro");
                }

                Mensajero mensajero = new Mensajero(id, nombre, capacidad, centroActual);
                mensajeroRepo.guardar(mensajero);

                // Asociar al centro
                Centro centro = centroRepo.buscarPorId(centroActual);
                if (centro != null) {
                    centro.getMensajeros().add(mensajero);
                }
            }

            // --- Paquetes ---
            NodeList paquetes = getNodes(config, "paquetes", "paquete", "Paquetes", "Paquete");
            for (int i = 0; i < paquetes.getLength(); i++) {
                Element e = (Element) paquetes.item(i);
                String id = getAttrOrChild(e, "id");
                String cliente = getAttrOrChild(e, "cliente");
                double peso = Double.parseDouble(getAttrOrChild(e, "peso"));
                String destino = getAttrOrChild(e, "destino");
                String centroActual = getAttrOrChild(e, "centroActual");

                Paquete paquete = new Paquete(id, cliente, peso, destino, centroActual);

                // Estado opcional en el enunciado
                String estadoStr = getAttrOrChild(e, "estado");
                if (estadoStr != null && !estadoStr.isEmpty()) {
                    EstadoPaquete estado = EstadoPaquete.valueOf(estadoStr.toUpperCase());
                    // Ajustar estado inicial si viene en XML
                    if (estado == EstadoPaquete.EN_TRANSITO) paquete.marcarEnTransito();
                    if (estado == EstadoPaquete.ENTREGADO) {
                        paquete.marcarEnTransito();
                        paquete.marcarEntregado();
                    }
                }

                paqueteRepo.guardar(paquete);

                // Asociar al centro
                Centro centro = centroRepo.buscarPorId(centroActual);
                if (centro != null) {
                    centro.agregarPaquete(paquete);
                }
            }

            // --- Solicitudes ---
            NodeList solicitudes = getNodes(config, "solicitudes", "solicitud", "Solicitudes", "Solicitud");
            for (int i = 0; i < solicitudes.getLength(); i++) {
                Element e = (Element) solicitudes.item(i);
                String id = getAttrOrChild(e, "id");
                String tipoAttr = getAttrOrChild(e, "tipo");
                // Soportar mayúsculas del XML y tu enum actual
                TipoSolicitud tipo = mapTipoSolicitud(tipoAttr);
                // Enunciado usa "paquete"; tu formato usa "paqueteId"
                String paqueteId = getAttrOrChild(e, "paqueteId");
                if (paqueteId == null || paqueteId.isEmpty()) {
                    paqueteId = getAttrOrChild(e, "paquete");
                }
                int prioridad = Integer.parseInt(getAttrOrChild(e, "prioridad"));

                Solicitud solicitud = new Solicitud(id, tipo, paqueteId, prioridad);
                solicitudRepo.guardar(solicitud);
            }

        } catch (Exception ex) {
            throw new RuntimeException("Error al importar datos desde XML: " + ex.getMessage(), ex);
        }
    }

    // Helpers

    // Obtiene lista de nodos soportando contenedores y etiquetas en minúsculas/mayúsculas
    private static NodeList getNodes(Element config,
                                     String containerLower, String itemLower,
                                     String containerUpper, String itemUpper) {
        NodeList container = config.getElementsByTagName(containerLower);
        if (container == null || container.getLength() == 0) {
            container = config.getElementsByTagName(containerUpper);
        }
        if (container != null && container.getLength() > 0) {
            Element cont = (Element) container.item(0);
            NodeList items = cont.getElementsByTagName(itemLower);
            if (items == null || items.getLength() == 0) {
                items = cont.getElementsByTagName(itemUpper);
            }
            return items;
        }
        // Si no hay contenedor, buscar directamente por item
        NodeList direct = config.getElementsByTagName(itemLower);
        if (direct == null || direct.getLength() == 0) {
            direct = config.getElementsByTagName(itemUpper);
        }
        return direct;
    }

    // Obtiene atributo si existe; si no, intenta leer como hijo <campo>texto</campo>
    private static String getAttrOrChild(Element e, String name) {
        String val = e.getAttribute(name);
        if (val != null && !val.isEmpty()) return val;
        NodeList children = e.getElementsByTagName(name);
        if (children != null && children.getLength() > 0) {
            return children.item(0).getTextContent();
        }
        // También probar mayúsculas
        children = e.getElementsByTagName(capitalize(name));
        if (children != null && children.getLength() > 0) {
            return children.item(0).getTextContent();
        }
        return null;
    }

    private static String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return s.substring(0,1).toUpperCase() + s.substring(1);
    }

    // Mapea valores del XML a tu enum actual (EnvioNormal, EnvioUrgente)
    private static TipoSolicitud mapTipoSolicitud(String raw) {
        if (raw == null) throw new IllegalArgumentException("Tipo de solicitud vacío");
        String t = raw.trim();
        // Aceptar variantes comunes
        if (t.equalsIgnoreCase("EnvioNormal") || t.equalsIgnoreCase("ENVIO_NORMAL") || t.equalsIgnoreCase("NORMAL")) {
            return TipoSolicitud.EnvioNormal;
        }
        if (t.equalsIgnoreCase("EnvioUrgente") || t.equalsIgnoreCase("ENVIO_URGENTE") || t.equalsIgnoreCase("EXPRESS") || t.equalsIgnoreCase("EnvioExpress") || t.equalsIgnoreCase("ENVIO_EXPRESS")) {
            return TipoSolicitud.EnvioUrgente;
        }
        // Si viene algo inesperado, lanzar error claro
        throw new IllegalArgumentException("TipoSolicitud no soportado: " + raw);
    }
}