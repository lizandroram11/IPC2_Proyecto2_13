import React, { useEffect, useState } from "react";
import { getSolicitudes, crearSolicitud } from "../services/api";

function Solicitudes() {
  const [solicitudes, setSolicitudes] = useState([]);
  const [form, setForm] = useState({ id: "", tipo: "EnvioNormal", paqueteId: "", prioridad: 1 });

  useEffect(() => {
    getSolicitudes().then(res => setSolicitudes(res.data));
  }, []);

  const handleSubmit = (e) => {
    e.preventDefault();
    crearSolicitud(form).then(() => {
      alert("Solicitud creada");
      getSolicitudes().then(res => setSolicitudes(res.data));
    });
  };

  return (
    <div>
      <h2>Gestión de Solicitudes</h2>
      <form onSubmit={handleSubmit}>
        <input placeholder="ID" onChange={e => setForm({ ...form, id: e.target.value })} />
        <select onChange={e => setForm({ ...form, tipo: e.target.value })}>
          <option value="EnvioNormal">Envio Normal</option>
          <option value="EnvioUrgente">Envio Urgente</option>
        </select>
        <input placeholder="Paquete ID" onChange={e => setForm({ ...form, paqueteId: e.target.value })} />
        <input type="number" placeholder="Prioridad" onChange={e => setForm({ ...form, prioridad: e.target.value })} />
        <button type="submit">Crear Solicitud</button>
      </form>

      <ul>
        {solicitudes.map(s => (
          <li key={s.id}>{s.tipo} - Paquete: {s.paqueteId} - Prioridad: {s.prioridad} - Estado: {s.estado}</li>
        ))}
      </ul>
    </div>
  );
}

export default Solicitudes;
