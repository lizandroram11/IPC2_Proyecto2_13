// src/components/Mensajeros.js
import React, { useEffect, useState } from "react";
import {
  getMensajeros,
  crearMensajero,
  cambiarEstadoMensajero,
  reasignarCentroMensajero,
} from "../services/api";
import "../styles/Mensajeros.css";

function Mensajeros() {
  const [mensajeros, setMensajeros] = useState([]);
  const [form, setForm] = useState({
    id: "",
    nombre: "",
    capacidad: 0,
    centroActual: "",
  });

  const cargar = () =>
    getMensajeros().then((res) => setMensajeros(Object.values(res.data)));

  useEffect(() => {
    cargar();
  }, []);

  const handleCrear = (e) => {
    e.preventDefault();
    crearMensajero(form).then(() => {
      alert("Mensajero creado");
      cargar();
    });
  };

  const handleEstado = (id, estado) => {
    cambiarEstadoMensajero(id, estado).then(() => {
      alert("Estado actualizado");
      cargar();
    });
  };

  const handleReasignar = (id) => {
    const nuevoCentro = prompt("Nuevo centro:");
    if (!nuevoCentro) return;
    reasignarCentroMensajero(id, nuevoCentro).then(() => {
      alert("Centro reasignado");
      cargar();
    });
  };

  return (
    <div className="mensajeros-container">
      <h2 className="titulo">Gestión de Mensajeros</h2>

      <form onSubmit={handleCrear} className="form-mensajeros">
        <input
          placeholder="ID"
          onChange={(e) => setForm({ ...form, id: e.target.value })}
        />
        <input
          placeholder="Nombre"
          onChange={(e) => setForm({ ...form, nombre: e.target.value })}
        />
        <input
          type="number"
          placeholder="Capacidad"
          onChange={(e) =>
            setForm({ ...form, capacidad: Number(e.target.value) })
          }
        />
        <input
          placeholder="Centro Actual"
          onChange={(e) => setForm({ ...form, centroActual: e.target.value })}
        />
        <button type="submit" className="btn-crear">
          Crear Mensajero
        </button>
      </form>

      <table className="tabla-mensajeros">
        <thead>
          <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Capacidad</th>
            <th>Centro</th>
            <th>Estado</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {mensajeros.map((m) => (
            <tr key={m.id}>
              <td>{m.id}</td>
              <td>{m.nombre}</td>
              <td>{m.capacidad}</td>
              <td>{m.centroActual}</td>
              <td>{m.estado}</td>
              <td>
                <button
                  className="btn-estado disponible"
                  onClick={() => handleEstado(m.id, "DISPONIBLE")}
                >
                  Disponible
                </button>
                <button
                  className="btn-estado transito"
                  onClick={() => handleEstado(m.id, "EN_TRANSITO")}
                >
                  En Tránsito
                </button>
                <button
                  className="btn-reasignar"
                  onClick={() => handleReasignar(m.id)}
                >
                  Reasignar Centro
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default Mensajeros;
