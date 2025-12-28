import React, { useEffect, useState } from "react";
import { getCentros } from "../services/api";

function Centros() {
  const [centros, setCentros] = useState([]);

  useEffect(() => {
    getCentros().then(res => {
      const data = Object.values(res.data);
      setCentros(data);
    });
  }, []);

  return (
    <div>
      <h2>Gestión de Centros</h2>
      <ul>
        {centros.map(c => (
          <li key={c.id}>
            {c.nombre} - {c.ciudad} (Capacidad: {c.capacidad})
          </li>
        ))}
      </ul>
    </div>
  );
}

export default Centros;
