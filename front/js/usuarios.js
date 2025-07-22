const token = localStorage.getItem("token");

if (!token) {
  alert("Você precisa estar logado.");
  window.location.href = "login.html";
}

const payload = JSON.parse(atob(token.split('.')[1]));
const role = payload.role || "";
const isAdmin = role === "ROLE_ADMIN";

const form = document.getElementById("formUsuario");
const tabela = document.getElementById("tabelaUsuarios");

async function carregarUsuarios() {
  try {
    const resposta = await fetch("http://localhost:8080/usuarios", {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });

    if (!resposta.ok) throw new Error("Erro ao carregar usuários.");

    const usuarios = await resposta.json();
    tabela.innerHTML = "";

    usuarios.forEach(usuario => {
      const linha = document.createElement("tr");

      linha.innerHTML = `
        <td>${usuario.id}</td>
        <td>${usuario.nome}</td>
        <td>${usuario.email}</td>
        <td style="text-align: center;">
          ${isAdmin ? `
            <button onclick="excluirUsuario(${usuario.id})" title="Excluir usuário" style="background: none; border: none; cursor: pointer;">
              🗑️
            </button>` : ""}
        </td>
      `;

      tabela.appendChild(linha);
    });
  } catch (erro) {
    console.error("Erro ao carregar usuários:", erro);
    tabela.innerHTML = "<tr><td colspan='4'>Não foi possível carregar os usuários.</td></tr>";
  }
}

form.addEventListener("submit", async (e) => {
  e.preventDefault();

  const nome = document.getElementById("nomeUsuario").value;
  const email = document.getElementById("emailUsuario").value;
  const senha = document.getElementById("senhaUsuario").value;

  try {
    const resposta = await fetch("http://localhost:8080/usuarios", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`
      },
      body: JSON.stringify({ nome, email, senha })
    });

    if (!resposta.ok) throw new Error("Erro ao cadastrar usuário.");

    form.reset();
    carregarUsuarios();
  } catch (erro) {
    console.error("Erro ao cadastrar usuário:", erro);
  }
});

async function excluirUsuario(id) {
  if (!confirm("Deseja realmente excluir este usuário?")) return;

  try {
    const resposta = await fetch(`http://localhost:8080/usuarios/${id}`, {
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${token}`
      }
    });

    if (!resposta.ok) throw new Error("Erro ao excluir usuário.");

    alert("Usuário excluído com sucesso.");
    carregarUsuarios();

  } catch (erro) {
    console.error("Erro ao excluir usuário:", erro);
  }
}

carregarUsuarios();