// src/components/Envios.js
import React, { useEffect, useState } from "react";
import axios from "axios";
import { getPaquetes, getMensajeros } from "../services/api";
import "../styles/Envios.css";

const API_URL = "http://localhost:8080/api";

function Envios() {
  const [paquetes, setPaquetes] = useState([]);
  const [mensajeros, setMensajeros] = useState([]);
  const [paqueteId, setPaqueteId] = useState("");
  const [mensajeroId, setMensajeroId] = useState("");
  const [mensaje, setMensaje] = useState("");

  useEffect(() => {
    getPaquetes().then(res => setPaquetes(Object.values(res.data)));
    getMensajeros().then(res => setMensajeros(Object.values(res.data)));
  }, []);

  const handleAsignar = (e) => {
    e.preventDefault();
    if (!paqueteId || !mensajeroId) {
      alert("Selecciona un paquete y un mensajero");
      return;
    }
    axios.put(`${API_URL}/envios/asignar?paqueteId=${paqueteId}&mensajeroId=${mensajeroId}`)
      .then(() => setMensaje("Envío asignado correctamente"))
      .catch(err => setMensaje("Error al asignar envío: " + err));
  };

  return (
    <div className="envios-container">
      <h2 className="titulo">Gestión de Envíos</h2>

      {/* Formulario de asignación */}
      <form onSubmit={handleAsignar} className="form-envios">
        <select value={paqueteId} onChange={e => setPaqueteId(e.target.value)}>
          <option value="">Selecciona Paquete</option>
          {paquetes.map(p => (
            <option key={p.id} value={p.id}>
              {p.id} - {p.cliente}
            </option>
          ))}
        </select>

        <select value={mensajeroId} onChange={e => setMensajeroId(e.target.value)}>
          <option value="">Selecciona Mensajero</option>
          {mensajeros.map(m => (
            <option key={m.id} value={m.id}>
              {m.id} - {m.nombre}
            </option>
          ))}
        </select>

        <button type="submit" className="btn-asignar">Asignar Mensajero</button>
      </form>

      {mensaje && (
        <div className="resultado-envios">
          <strong>{mensaje}</strong>
        </div>
      )}

      {/* Tabla de paquetes */}
      <h3>Paquetes Disponibles</h3>
      <table className="tabla-envios">
        <thead>
          <tr>
            <th>ID</th>
            <th>Cliente</th>
            <th>Destino</th>
            <th>Estado</th>
          </tr>
        </thead>
        <tbody>
          {paquetes.map(p => (
            <tr key={p.id}>
              <td>{p.id}</td>
              <td>{p.cliente}</td>
              <td>{p.destino}</td>
              <td>{p.estado}</td>
            </tr>
          ))}
        </tbody>
      </table>

      {/* Tabla de mensajeros */}
      <h3>Mensajeros Disponibles</h3>
      <table className="tabla-envios">
        <thead>
          <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Centro</th>
            <th>Estado</th>
          </tr>
        </thead>
        <tbody>
          {mensajeros.map(m => (
            <tr key={m.id}>
              <td>{m.id}</td>
              <td>{m.nombre}</td>
              <td>{m.centroActual}</td>
              <td>{m.estado}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default Envios;
