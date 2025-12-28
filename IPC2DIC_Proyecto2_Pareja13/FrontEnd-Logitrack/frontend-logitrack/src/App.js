import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import './App.css';

// Importa tu Navbar
import AppNavbar from "./components/Navbar";

// Importa tus componentes
import Centros from "./components/Centros";
import Mensajeros from "./components/Mensajeros";
import Paquetes from "./components/Paquetes";
import Solicitudes from "./components/Solicitudes";
import Reporte from "./components/Reporte";
import Rutas from "./components/Rutas";
import Importar from "./components/Importar";   // nuevo componente

function App() {
  return (
    <Router>
      {/* Barra de navegación */}
      <AppNavbar />

      {/* Contenido principal */}
      <div className="container mt-4">
        <Routes>
          {/* Centros */}
          <Route path="/centros" element={<Centros />} />
          <Route path="/centros/crear" element={<Centros />} />

          {/* Rutas */}
          <Route path="/rutas" element={<Rutas />} />
          <Route path="/rutas/crear" element={<Rutas />} />

          {/* Mensajeros */}
          <Route path="/mensajeros" element={<Mensajeros />} />
          <Route path="/mensajeros/crear" element={<Mensajeros />} />

          {/* Paquetes */}
          <Route path="/paquetes" element={<Paquetes />} />
          <Route path="/paquetes/crear" element={<Paquetes />} />

          {/* Solicitudes */}
          <Route path="/solicitudes" element={<Solicitudes />} />
          <Route path="/solicitudes/crear" element={<Solicitudes />} />

          {/* Reporte */}
          <Route path="/reporte" element={<Reporte />} />

          {/* Importar */}
          <Route path="/importar" element={<Importar />} /> {/* nueva ruta */}

          {/* Ruta por defecto */}
          <Route path="/" element={<h2>Bienvenido a LogiTrack 🚚</h2>} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
