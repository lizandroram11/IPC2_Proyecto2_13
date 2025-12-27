package com.ipc2dic_proyecto2_pareja13.util;

import com.ipc2dic_proyecto2_pareja13.model.*;
import com.ipc2dic_proyecto2_pareja13.repository.*;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XmlExporter {

    public static void exportarReporte(String rutaArchivo,
                                       CentroRepository centroRepo,
                                       MensajeroRepository mensajeroRepo,
                                       PaqueteRepository paqueteRepo,
                                       SolicitudRepository solicitudRepo) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element root = doc.createElement("resultadoLogitrack");
            doc.appendChild(root);

            // --- Estadísticas ---
            Element estadisticas = doc.createElement("estadisticas");
            root.appendChild(estadisticas);

            int paquetesProcesados = (int) paqueteRepo.listarTodos().values().stream()
                    .filter(p -> p.getEstado() != EstadoPaquete.PENDIENTE)
                    .count();

            int solicitudesAtendidas = (int) solicitudRepo.listarTodas().stream()
                    .filter(s -> s.getEstado() == EstadoSolicitud.ATENDIDA)
                    .count();

            int mensajerosActivos = (int) mensajeroRepo.listarTodos().values().stream()
                    .filter(m -> m.getEstado() == EstadoMensajero.EN_TRANSITO)
                    .count();

            estadisticas.appendChild(crearElemento(doc, "paquetesProcesados", String.valueOf(paquetesProcesados)));
            estadisticas.appendChild(crearElemento(doc, "solicitudesAtendidas", String.valueOf(solicitudesAtendidas)));
            estadisticas.appendChild(crearElemento(doc, "mensajerosActivos", String.valueOf(mensajerosActivos)));

            // --- Centros ---
            Element centrosElement = doc.createElement("centros");
            root.appendChild(centrosElement);
            for (Centro centro : centroRepo.listarTodos().values()) {
                Element centroElement = doc.createElement("centro");
                centroElement.setAttribute("id", centro.getId());

                centroElement.appendChild(crearElemento(doc, "paquetesActuales", String.valueOf(centro.getPaquetes().size())));
                long disponibles = centro.getMensajeros().stream()
                        .filter(m -> m.getEstado() == EstadoMensajero.DISPONIBLE)
                        .count();
                centroElement.appendChild(crearElemento(doc, "mensajerosDisponibles", String.valueOf(disponibles)));

                centrosElement.appendChild(centroElement);
            }

            // --- Mensajeros ---
            Element mensajerosElement = doc.createElement("mensajeros");
            root.appendChild(mensajerosElement);
            for (Mensajero m : mensajeroRepo.listarTodos().values()) {
                Element mensajeroElement = doc.createElement("mensajero");
                mensajeroElement.setAttribute("id", m.getId());
                mensajeroElement.setAttribute("estado", m.getEstado().name());
                mensajerosElement.appendChild(mensajeroElement);
            }

            // --- Paquetes ---
            Element paquetesElement = doc.createElement("paquetes");
            root.appendChild(paquetesElement);
            for (Paquete p : paqueteRepo.listarTodos().values()) {
                Element paqueteElement = doc.createElement("paquete");
                paqueteElement.setAttribute("id", p.getId());
                paqueteElement.setAttribute("estado", p.getEstado().name());
                paqueteElement.setAttribute("centroActual", p.getCentroActual());
                paquetesElement.appendChild(paqueteElement);
            }

            // --- Solicitudes ---
            Element solicitudesElement = doc.createElement("solicitudes");
            root.appendChild(solicitudesElement);
            for (Solicitud s : solicitudRepo.listarTodas()) {
                Element solicitudElement = doc.createElement("solicitud");
                solicitudElement.setAttribute("id", s.getId());
                solicitudElement.setAttribute("estado", s.getEstado().name());
                solicitudElement.setAttribute("paquete", s.getPaqueteId());
                solicitudesElement.appendChild(solicitudElement);
            }

            // Guardar archivo
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(doc), new StreamResult(new File(rutaArchivo)));

        } catch (Exception ex) {
            throw new RuntimeException("Error al exportar reporte a XML: " + ex.getMessage(), ex);
        }
    }

    private static Element crearElemento(Document doc, String nombre, String valor) {
        Element elem = doc.createElement(nombre);
        elem.setTextContent(valor);
        return elem;
    }
}