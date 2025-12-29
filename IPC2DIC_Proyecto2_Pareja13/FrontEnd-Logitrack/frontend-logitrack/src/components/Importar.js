// src/components/Importar.js
import React, { useState } from "react";
import { importarArchivo } from "../services/api";
import "../styles/Importar.css";

function Importar() {
  const [file, setFile] = useState(null);
  const [mensaje, setMensaje] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!file) {
      alert("Selecciona un archivo XML");
      return;
    }
    importarArchivo(file)
      .then((res) => setMensaje(res.data))
      .catch((err) => setMensaje("Error al importar: " + err));
  };

  return (
    <div className="importar-container">
      <h2 className="titulo">Importar XML</h2>
      <form onSubmit={handleSubmit} className="form-importar">
        <input
          type="file"
          accept=".xml"
          onChange={(e) => setFile(e.target.files[0])}
        />
        <button type="submit" className="btn-importar">
          Importar
        </button>
      </form>

      {mensaje && (
        <div className="resultado-importar">
          <strong>{mensaje}</strong>
        </div>
      )}
    </div>
  );
}

export default Importar;
