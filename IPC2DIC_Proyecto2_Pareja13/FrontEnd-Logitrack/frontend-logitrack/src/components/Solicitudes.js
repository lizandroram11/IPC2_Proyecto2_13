// src/components/Solicitudes.js
import React, { useEffect, useState } from "react";
import {
  getSolicitudes,
  crearSolicitud,
  procesarSolicitud,
  procesarNSolicitudes,
} from "../services/api";
import "../styles/Solicitudes.css";

function Solicitudes() {
  const [solicitudes, setSolicitudes] = useState([]);
  const [form, setForm] = useState({
    id: "",
    tipo: "EnvioNormal",
    paqueteId: "",
    prioridad: 1,
  });
  const [nProcesar, setNProcesar] = useState(1);
  const [resultado, setResultado] = useState(null);

  const cargar = () =>
    getSolicitudes().then((res) => {
      // Si el backend devuelve array directo:
      if (Array.isArray(res.data)) setSolicitudes(res.data);
      // Si devuelve Map:
      else setSolicitudes(Object.values(res.data));
    });

  useEffect(() => {
    cargar();
  }, []);

  const handleCrear = (e) => {
    e.preventDefault();
    crearSolicitud(form).then(() => {
      alert("Solicitud creada");
      setForm({ id: "", tipo: "EnvioNormal", paqueteId: "", prioridad: 1 });
      cargar();
    });
  };

  const handleProcesarUna = () => {
    procesarSolicitud()
      .then((res) => {
        setResultado(res.data);
        alert("Se procesó 1 solicitud");
        cargar();
      })
      .catch((err) => alert("Error al procesar: " + err));
  };

  const handleProcesarVarias = () => {
    if (!nProcesar || nProcesar < 1) {
      alert("Ingresa un número válido de solicitudes a procesar");
      return;
    }
    procesarNSolicitudes(nProcesar)
      .then((res) => {
        setResultado(res.data);
        alert(`Se procesaron ${nProcesar} solicitudes`);
        cargar();
      })
      .catch((err) => alert("Error al procesar varias: " + err));
  };

  return (
    <div className="solicitudes-container">
      <h2 className="titulo">Gestión de Solicitudes</h2>

      {/* Formulario de creación */}
      <form onSubmit={handleCrear} className="form-solicitudes">
        <input
          placeholder="ID"
          value={form.id}
          onChange={(e) => setForm({ ...form, id: e.target.value })}
        />
        <select
          value={form.tipo}
          onChange={(e) => setForm({ ...form, tipo: e.target.value })}
        >
          <option value="EnvioNormal">EnvioNormal</option>
          <option value="EnvioUrgente">EnvioUrgente</option>
        </select>
        <input
          placeholder="Paquete ID"
          value={form.paqueteId}
          onChange={(e) => setForm({ ...form, paqueteId: e.target.value })}
        />
        <input
          type="number"
          placeholder="Prioridad"
          value={form.prioridad}
          onChange={(e) =>
            setForm({ ...form, prioridad: Number(e.target.value) })
          }
        />
        <button type="submit" className="btn-crear">
          Crear Solicitud
        </button>
      </form>

      {/* Acciones de procesamiento */}
      <div className="acciones-procesar">
        <button className="btn-procesar uno" onClick={handleProcesarUna}>
          Procesar 1 solicitud
        </button>
        <input
          type="number"
          min="1"
          value={nProcesar}
          onChange={(e) => setNProcesar(Number(e.target.value))}
          className="input-n"
        />
        <button className="btn-procesar varias" onClick={handleProcesarVarias}>
          Procesar N solicitudes
        </button>
      </div>

      {/* Resultado del procesamiento */}
      {resultado && (
        <div className="resultado-procesar">
          <h4>Resultado</h4>
          <pre>{JSON.stringify(resultado, null, 2)}</pre>
        </div>
      )}

      {/* Tabla de solicitudes */}
      <table className="tabla-solicitudes">
        <thead>
          <tr>
            <th>ID</th>
            <th>Tipo</th>
            <th>Paquete</th>
            <th>Prioridad</th>
            <th>Estado</th>
          </tr>
        </thead>
        <tbody>
          {solicitudes.map((s) => (
            <tr key={s.id}>
              <td>{s.id}</td>
              <td>{s.tipo}</td>
              <td>{s.paqueteId}</td>
              <td>{s.prioridad}</td>
              <td>{s.estado}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default Solicitudes;
