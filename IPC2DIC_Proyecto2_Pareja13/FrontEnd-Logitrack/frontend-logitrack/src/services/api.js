// src/services/api.js
import axios from "axios";

const API_URL = "http://localhost:8080/api";

// Centros
export const getCentros = () => axios.get(`${API_URL}/centros`);

// Rutas
export const getRutas = () => axios.get(`${API_URL}/rutas`);
export const crearRuta = (ruta) => axios.post(`${API_URL}/rutas`, ruta);
export const actualizarRuta = (id, ruta) => axios.put(`${API_URL}/rutas/${id}`, ruta);
export const eliminarRuta = (id) => axios.delete(`${API_URL}/rutas/${id}`);

// Mensajeros
export const getMensajeros = () => axios.get(`${API_URL}/mensajeros`);
export const crearMensajero = (mensajero) => axios.post(`${API_URL}/mensajeros`, mensajero);
export const cambiarEstadoMensajero = (id, estado) =>
  axios.put(`${API_URL}/mensajeros/${id}/estado?estado=${estado}`);
export const reasignarCentroMensajero = (id, nuevoCentro) =>
  axios.put(`${API_URL}/mensajeros/${id}/centro?nuevoCentro=${nuevoCentro}`);

// Paquetes
export const getPaquetes = () => axios.get(`${API_URL}/paquetes`);
export const crearPaquete = (paquete) => axios.post(`${API_URL}/paquetes`, paquete);
export const actualizarPaquete = (id, paquete) => axios.put(`${API_URL}/paquetes/${id}`, paquete);
export const eliminarPaquete = (id) => axios.delete(`${API_URL}/paquetes/${id}`);
export const actualizarEstadoPaquete = (id, nuevoEstado) =>
  axios.put(`${API_URL}/envios/${id}/estado?nuevoEstado=${nuevoEstado}`);

// Solicitudes
export const getSolicitudes = () => axios.get(`${API_URL}/solicitudes`);
export const crearSolicitud = (solicitud) => axios.post(`${API_URL}/solicitudes`, solicitud);
export const procesarSolicitud = () => axios.post(`${API_URL}/solicitudes/procesar`);
export const procesarNSolicitudes = (n) => axios.post(`${API_URL}/solicitudes/procesar/${n}`);

// Reporte (si tu backend tiene este endpoint)
export const generarReporte = () => axios.get(`${API_URL}/reporte`);

// Importación XML
export const importarArchivo = (file) => {
  const formData = new FormData();
  formData.append("file", file);
  return axios.post(`${API_URL}/importar`, formData, {
    headers: { "Content-Type": "multipart/form-data" },
  });
};
