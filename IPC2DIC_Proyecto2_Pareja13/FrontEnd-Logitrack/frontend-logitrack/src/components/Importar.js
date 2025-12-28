// src/components/Importar.js
import React, { useState } from "react";
import { importarArchivo } from "../services/api";

function Importar() {
  const [file, setFile] = useState(null);
  const [mensaje, setMensaje] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!file) {
      alert("Por favor selecciona un archivo XML");
      return;
    }

    importarArchivo(file)
      .then(res => setMensaje(res.data))
      .catch(err => setMensaje("Error al importar: " + err));
  };

  return (
    <div>
      <h2>Importar Datos desde XML</h2>
      <form onSubmit={handleSubmit}>
        <input type="file" accept=".xml" onChange={e => setFile(e.target.files[0])} />
        <button type="submit">Importar</button>
      </form>

      {mensaje && (
        <div style={{ marginTop: "20px" }}>
          <strong>{mensaje}</strong>
        </div>
      )}
    </div>
  );
}

export default Importar;

