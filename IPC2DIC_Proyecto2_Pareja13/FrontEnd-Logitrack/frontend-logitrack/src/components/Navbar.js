// src/components/AppNavbar.js
import React from "react";
import { Link } from "react-router-dom";
import { Navbar, Nav, NavDropdown, Container } from "react-bootstrap";
import "../styles/Navbar.css"; // custom overrides

function AppNavbar() {
  return (
    <Navbar bg="dark" variant="dark" expand="lg" className="app-navbar">
      <Container>
        <Navbar.Brand as={Link} to="/" className="brand">
          LogiTrack
        </Navbar.Brand>

        <Navbar.Toggle aria-controls="main-navbar" />
        <Navbar.Collapse id="main-navbar">
          <Nav className="me-auto">

            {/* Centros */}
            <NavDropdown title="Centros" id="centros-dropdown">
              <NavDropdown.Item as={Link} to="/centros">Listar Centros</NavDropdown.Item>
            </NavDropdown>

            {/* Rutas */}
            <NavDropdown title="Rutas" id="rutas-dropdown">
              <NavDropdown.Item as={Link} to="/rutas">Listar Rutas</NavDropdown.Item>
            </NavDropdown>

            {/* Mensajeros */}
            <NavDropdown title="Mensajeros" id="mensajeros-dropdown">
              <NavDropdown.Item as={Link} to="/mensajeros">Listar Mensajeros</NavDropdown.Item>
            </NavDropdown>

            {/* Paquetes */}
            <NavDropdown title="Paquetes" id="paquetes-dropdown">
              <NavDropdown.Item as={Link} to="/paquetes">Listar Paquetes</NavDropdown.Item>
            </NavDropdown>

            {/* Solicitudes */}
            <NavDropdown title="Solicitudes" id="solicitudes-dropdown">
              <NavDropdown.Item as={Link} to="/solicitudes">Listar Solicitudes</NavDropdown.Item>
            </NavDropdown>

            {/* Envíos */}
            <Nav.Link as={Link} to="/envios">Envíos</Nav.Link>

            {/* Reporte */}
            <Nav.Link as={Link} to="/reporte">Reporte</Nav.Link>

            {/* Importar */}
            <Nav.Link as={Link} to="/importar">Importar</Nav.Link>

          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}

export default AppNavbar;
