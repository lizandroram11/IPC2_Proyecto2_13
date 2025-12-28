// src/components/Reporte.js
import React, { useState } from "react";
import { generarReporte } from "../services/api";

function Reporte() {
  const [reporte, setReporte] = useState(null);

  const handleGenerar = () => {
    generarReporte()
      .then(res => setReporte(res.data))
      .catch(err => alert("Error al generar reporte: " + err));
  };

  return (
    <div>
      <h2>Gestión de Reporte</h2>
      <button onClick={handleGenerar}>Generar Reporte</button>

      {reporte && (
        <div style={{ marginTop: "20px" }}>
          <h3>Resultado del Reporte:</h3>
          <pre>{JSON.stringify(reporte, null, 2)}</pre>
        </div>
      )}
    </div>
  );
}

export default Reporte;
