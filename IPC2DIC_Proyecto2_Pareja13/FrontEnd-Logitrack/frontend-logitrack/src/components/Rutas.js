import React, { useEffect, useState } from "react";
import { getRutas, crearRuta } from "../services/api";

function Rutas() {
  const [rutas, setRutas] = useState([]);
  const [form, setForm] = useState({ id: "", origen: "", destino: "", distancia: 0 });

  useEffect(() => {
    getRutas().then(res => setRutas(Object.values(res.data)));
  }, []);

  const handleSubmit = (e) => {
    e.preventDefault();
    crearRuta(form).then(() => {
      alert("Ruta creada");
      getRutas().then(res => setRutas(Object.values(res.data)));
    });
  };

  return (
    <div>
      <h2>Gestión de Rutas</h2>
      <form onSubmit={handleSubmit}>
        <input placeholder="ID" onChange={e => setForm({ ...form, id: e.target.value })} />
        <input placeholder="Origen" onChange={e => setForm({ ...form, origen: e.target.value })} />
        <input placeholder="Destino" onChange={e => setForm({ ...form, destino: e.target.value })} />
        <input type="number" placeholder="Distancia" onChange={e => setForm({ ...form, distancia: e.target.value })} />
        <button type="submit">Crear Ruta</button>
      </form>

      <ul>
        {rutas.map(r => (
          <li key={r.id}>{r.origen} → {r.destino} ({r.distancia} km)</li>
        ))}
      </ul>
    </div>
  );
}

export default Rutas;
