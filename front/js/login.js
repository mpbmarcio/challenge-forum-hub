document.getElementById("loginForm").addEventListener("submit", async (e) => {
  e.preventDefault();

  const email = document.getElementById("email").value;
  const senha = document.getElementById("senha").value;

  try {
    const resposta = await fetch("http://localhost:8080/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ email, senha })
    });

    if (!resposta.ok) throw new Error("Login inválido");

    const dados = await resposta.json();

    // ✅ Salvar token no localStorage
    localStorage.setItem("token", dados.token);

    // ✅ Validar e salvar ID do usuário
    if (dados.usuario && dados.usuario.id) {
      localStorage.setItem("usuarioId", dados.usuario.id);
      console.log("🔑 Login bem-sucedido. ID do usuário:", dados.usuario.id);
    } else {
      console.warn("⚠️ ID do usuário não retornado no login.");
    }

    window.location.href = "index.html";

  } catch (erro) {
    document.getElementById("mensagemErro").textContent = "Usuário ou senha inválidos.";
    console.error("Erro no login:", erro);
  }
});