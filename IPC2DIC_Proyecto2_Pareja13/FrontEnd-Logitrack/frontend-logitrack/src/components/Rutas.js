// src/components/Rutas.js
import React, { useEffect, useState } from "react";
import {
  getRutas,
  crearRuta,
  actualizarRuta,
  eliminarRuta,
} from "../services/api";
import "../styles/Rutas.css";

function Rutas() {
  const [rutas, setRutas] = useState([]);
  const [form, setForm] = useState({
    id: "",
    origen: "",
    destino: "",
    distancia: 0,
  });

  const cargar = () =>
    getRutas().then((res) => setRutas(Object.values(res.data)));

  useEffect(() => {
    cargar();
  }, []);

  const handleCrear = (e) => {
    e.preventDefault();
    crearRuta(form).then(() => {
      alert("Ruta creada");
      cargar();
    });
  };

  const handleActualizar = (id) => {
    const ruta = rutas.find((r) => r.id === id);
    actualizarRuta(id, ruta).then(() => {
      alert("Ruta actualizada");
      cargar();
    });
  };

  const handleEliminar = (id) => {
    eliminarRuta(id).then(() => {
      alert("Ruta eliminada");
      cargar();
    });
  };

  return (
    <div className="rutas-container">
      <h2 className="titulo">Gestión de Rutas</h2>

      <form onSubmit={handleCrear} className="form-rutas">
        <input
          placeholder="ID"
          onChange={(e) => setForm({ ...form, id: e.target.value })}
        />
        <input
          placeholder="Origen"
          onChange={(e) => setForm({ ...form, origen: e.target.value })}
        />
        <input
          placeholder="Destino"
          onChange={(e) => setForm({ ...form, destino: e.target.value })}
        />
        <input
          type="number"
          placeholder="Distancia"
          onChange={(e) =>
            setForm({ ...form, distancia: Number(e.target.value) })
          }
        />
        <button type="submit" className="btn-crear">
          Crear Ruta
        </button>
      </form>

      <table className="tabla-rutas">
        <thead>
          <tr>
            <th>ID</th>
            <th>Origen</th>
            <th>Destino</th>
            <th>Distancia</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {rutas.map((r) => (
            <tr key={r.id}>
              <td>{r.id}</td>
              <td>{r.origen}</td>
              <td>{r.destino}</td>
              <td>{r.distancia}</td>
              <td>
                <button
                  className="btn-accion actualizar"
                  onClick={() => handleActualizar(r.id)}
                >
                  Actualizar
                </button>
                <button
                  className="btn-accion eliminar"
                  onClick={() => handleEliminar(r.id)}
                >
                  Eliminar
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default Rutas;
