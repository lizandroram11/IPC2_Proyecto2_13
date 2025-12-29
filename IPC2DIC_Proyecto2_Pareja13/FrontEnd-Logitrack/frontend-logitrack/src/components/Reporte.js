// src/components/Reporte.js
import React, { useState } from "react";
import { generarReporte } from "../services/api";
import "../styles/Reporte.css";

function Reporte() {
  const [reporte, setReporte] = useState(null);

  const handleGenerar = () => {
    generarReporte()
      .then((res) => setReporte(res.data))
      .catch((err) => alert("Error al generar reporte: " + err));
  };

  return (
    <div className="reporte-container">
      <h2 className="titulo">Generar Reporte</h2>
      <button onClick={handleGenerar} className="btn-reporte">
        Generar Reporte
      </button>

      {reporte && (
        <div className="resultado-reporte">
          <h4>Resultado</h4>
          <pre>{JSON.stringify(reporte, null, 2)}</pre>
        </div>
      )}
    </div>
  );
}

export default Reporte;
