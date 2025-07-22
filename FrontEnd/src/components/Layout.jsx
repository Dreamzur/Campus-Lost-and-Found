import Navbar from "./Navbar";
import Footer from "./Footer";
import { Outlet } from "react-router-dom";
import { useState, useEffect } from "react";

export default function Layout() {
  const [isAdmin, setIsAdmin] = useState(false);

  useEffect(() => {
  const checkLogin = () => {
    const loggedIn = localStorage.getItem("isAdmin") === "true";
    setIsAdmin(loggedIn);
  };

  checkLogin();

  window.addEventListener("storage", checkLogin);
  return () => window.removeEventListener("storage", checkLogin);
});

  return (
    <>
      <Navbar isAdmin={isAdmin} />
      <main>
        <Outlet />
      </main>
      <Footer />
    </>
  );
}