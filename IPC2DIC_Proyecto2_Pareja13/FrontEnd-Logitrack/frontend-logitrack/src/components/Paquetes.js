import React, { useEffect, useState } from "react";
import { getPaquetes, crearPaquete, actualizarPaquete, eliminarPaquete, actualizarEstadoPaquete } from "../services/api";

function Paquetes() {
  const [paquetes, setPaquetes] = useState([]);
  const [form, setForm] = useState({ id: "", cliente: "", peso: 0, destino: "", centroActual: "" });

  const cargarPaquetes = () => {
    getPaquetes().then(res => setPaquetes(Object.values(res.data)));
  };

  useEffect(() => {
    cargarPaquetes();
  }, []);

  const handleCrear = (e) => {
    e.preventDefault();
    crearPaquete(form).then(() => {
      alert(" Paquete creado");
      cargarPaquetes();
    });
  };

  const handleActualizar = (id) => {
    const paquete = paquetes.find(p => p.id === id);
    actualizarPaquete(id, paquete).then(() => {
      alert("Paquete actualizado");
      cargarPaquetes();
    });
  };

  const handleEliminar = (id) => {
    eliminarPaquete(id).then(() => {
      alert("Paquete eliminado");
      cargarPaquetes();
    });
  };

  const handleEstado = (id, nuevoEstado) => {
    actualizarEstadoPaquete(id, nuevoEstado).then(() => {
      alert("Estado actualizado");
      cargarPaquetes();
    });
  };

  return (
    <div>
      <h2>Gestión de Paquetes</h2>

      {/* Formulario de creación */}
      <form onSubmit={handleCrear}>
        <input placeholder="ID" onChange={e => setForm({ ...form, id: e.target.value })} />
        <input placeholder="Cliente" onChange={e => setForm({ ...form, cliente: e.target.value })} />
        <input type="number" placeholder="Peso" onChange={e => setForm({ ...form, peso: e.target.value })} />
        <input placeholder="Destino" onChange={e => setForm({ ...form, destino: e.target.value })} />
        <input placeholder="Centro Actual" onChange={e => setForm({ ...form, centroActual: e.target.value })} />
        <button type="submit">Crear Paquete</button>
      </form>

      {/* Tabla de paquetes */}
      <table border="1" style={{ marginTop: "20px", width: "100%" }}>
        <thead>
          <tr>
            <th>ID</th>
            <th>Cliente</th>
            <th>Peso</th>
            <th>Destino</th>
            <th>Centro</th>
            <th>Estado</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {paquetes.map(p => (
            <tr key={p.id}>
              <td>{p.id}</td>
              <td>{p.cliente}</td>
              <td>{p.peso}</td>
              <td>{p.destino}</td>
              <td>{p.centroActual}</td>
              <td>{p.estado}</td>
              <td>
                <button onClick={() => handleActualizar(p.id)}>Actualizar</button>
                <button onClick={() => handleEliminar(p.id)}>Eliminar</button>
                <button onClick={() => handleEstado(p.id, "EN_TRANSITO")}>Marcar En Tránsito</button>
                <button onClick={() => handleEstado(p.id, "ENTREGADO")}>Marcar Entregado</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default Paquetes;
