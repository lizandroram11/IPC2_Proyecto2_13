// src/components/Paquetes.js
import React, { useEffect, useState } from "react";
import {
  getPaquetes,
  crearPaquete,
  actualizarPaquete,
  eliminarPaquete,
  actualizarEstadoPaquete,
} from "../services/api";
import "../styles/Paquetes.css"; // Importa tu CSS

function Paquetes() {
  const [paquetes, setPaquetes] = useState([]);
  const [form, setForm] = useState({
    id: "",
    cliente: "",
    peso: 0,
    destino: "",
    centroActual: "",
  });

  const cargar = () =>
    getPaquetes().then((res) => setPaquetes(Object.values(res.data)));

  useEffect(() => {
    cargar();
  }, []);

  const handleCrear = (e) => {
    e.preventDefault();
    crearPaquete(form).then(() => {
      alert("Paquete creado");
      cargar();
    });
  };

  const handleActualizar = (id) => {
    const paquete = paquetes.find((p) => p.id === id);
    actualizarPaquete(id, paquete).then(() => {
      alert("Paquete actualizado");
      cargar();
    });
  };

  const handleEliminar = (id) => {
    eliminarPaquete(id).then(() => {
      alert("Paquete eliminado");
      cargar();
    });
  };

  const handleEstado = (id, nuevoEstado) => {
    actualizarEstadoPaquete(id, nuevoEstado).then(() => {
      alert("Estado actualizado");
      cargar();
    });
  };

  return (
    <div className="paquetes-container">
      <h2 className="titulo">Gestión de Paquetes</h2>

      {/* Formulario de creación */}
      <form onSubmit={handleCrear} className="form-paquete">
        <input
          placeholder="ID"
          onChange={(e) => setForm({ ...form, id: e.target.value })}
        />
        <input
          placeholder="Cliente"
          onChange={(e) => setForm({ ...form, cliente: e.target.value })}
        />
        <input
          type="number"
          placeholder="Peso"
          onChange={(e) => setForm({ ...form, peso: Number(e.target.value) })}
        />
        <input
          placeholder="Destino"
          onChange={(e) => setForm({ ...form, destino: e.target.value })}
        />
        <input
          placeholder="Centro Actual"
          onChange={(e) =>
            setForm({ ...form, centroActual: e.target.value })
          }
        />
        <button type="submit" className="btn-crear">
          Crear Paquete
        </button>
      </form>

      {/* Tabla de paquetes */}
      <table className="tabla-paquetes">
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
          {paquetes.map((p) => (
            <tr key={p.id}>
              <td>{p.id}</td>
              <td>{p.cliente}</td>
              <td>{p.peso}</td>
              <td>{p.destino}</td>
              <td>{p.centroActual}</td>
              <td>{p.estado}</td>
              <td>
                <button
                  className="btn-accion actualizar"
                  onClick={() => handleActualizar(p.id)}
                >
                  Actualizar
                </button>
                <button
                  className="btn-accion eliminar"
                  onClick={() => handleEliminar(p.id)}
                >
                  Eliminar
                </button>
                <button
                  className="btn-accion transito"
                  onClick={() => handleEstado(p.id, "EN_TRANSITO")}
                >
                  En Tránsito
                </button>
                <button
                  className="btn-accion entregado"
                  onClick={() => handleEstado(p.id, "ENTREGADO")}
                >
                  Entregado
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default Paquetes;
