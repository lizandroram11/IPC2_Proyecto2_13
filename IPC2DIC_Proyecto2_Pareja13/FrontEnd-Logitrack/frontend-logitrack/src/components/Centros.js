// src/components/Centros.js
import React, { useEffect, useState } from "react";
import { getCentros } from "../services/api";
import "../styles/Centros.css";

function Centros() {
  const [centros, setCentros] = useState([]);

  useEffect(() => {
    getCentros().then(res => setCentros(Object.values(res.data)));
  }, []);

  return (
    <div className="centros-container">
      <h2 className="titulo">Gestión de Centros</h2>

      <table className="tabla-centros">
        <thead>
          <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Ciudad</th>
            <th>Capacidad</th>
          </tr>
        </thead>
        <tbody>
          {centros.map(c => (
            <tr key={c.id}>
              <td>{c.id}</td>
              <td>{c.nombre}</td>
              <td>{c.ciudad}</td>
              <td>{c.capacidad}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default Centros;
