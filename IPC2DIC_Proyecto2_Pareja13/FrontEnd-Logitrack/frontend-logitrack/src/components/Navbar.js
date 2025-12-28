import React from "react";
import { Link } from "react-router-dom";
import { Navbar, Nav, NavDropdown, Container } from "react-bootstrap";

function AppNavbar() {
  return (
    <Navbar bg="light" expand="lg">
      <Container>
        <Navbar.Brand as={Link} to="/">LogiTrack</Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto">

            {/* Centros */}
            <NavDropdown title="Centros" id="centros-dropdown">
              <NavDropdown.Item as={Link} to="/centros">Listar Centros</NavDropdown.Item>
              <NavDropdown.Item as={Link} to="/centros/crear">Crear Centro</NavDropdown.Item>
            </NavDropdown>

            {/* Rutas */}
            <NavDropdown title="Rutas" id="rutas-dropdown">
              <NavDropdown.Item as={Link} to="/rutas">Listar Rutas</NavDropdown.Item>
              <NavDropdown.Item as={Link} to="/rutas/crear">Crear Ruta</NavDropdown.Item>
            </NavDropdown>

            {/* Mensajeros */}
            <NavDropdown title="Mensajeros" id="mensajeros-dropdown">
              <NavDropdown.Item as={Link} to="/mensajeros">Listar Mensajeros</NavDropdown.Item>
              <NavDropdown.Item as={Link} to="/mensajeros/crear">Crear Mensajero</NavDropdown.Item>
            </NavDropdown>

            {/* Paquetes */}
            <NavDropdown title="Paquetes" id="paquetes-dropdown">
              <NavDropdown.Item as={Link} to="/paquetes">Listar Paquetes</NavDropdown.Item>
              <NavDropdown.Item as={Link} to="/paquetes/crear">Crear Paquete</NavDropdown.Item>
            </NavDropdown>

            {/* Solicitudes */}
            <NavDropdown title="Solicitudes" id="solicitudes-dropdown">
              <NavDropdown.Item as={Link} to="/solicitudes">Listar Solicitudes</NavDropdown.Item>
              <NavDropdown.Item as={Link} to="/solicitudes/crear">Crear Solicitud</NavDropdown.Item>
            </NavDropdown>

            {/* Reporte */}
            <Nav.Link as={Link} to="/reporte">Reporte</Nav.Link>

            {/* Importar */}
            <Nav.Link as={Link} to="/importar">Importar</Nav.Link> {/* 👈 nueva opción */}

          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}

export default AppNavbar;
