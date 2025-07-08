# 📘 Commit Guidelines

Este projeto segue o padrão semântico para mensagens de commit, garantindo clareza e consistência no histórico de alterações.

## 🔤 Prefixos semânticos permitidos:

| Prefixo   | Descrição                                  | Exemplo prático                              |
|-----------|---------------------------------------------|----------------------------------------------|
| `feat`    | Nova funcionalidade                         | feat(schema-db): adiciona estrutura inicial  |
| `fix`     | Correção de bug                             | fix(auth): corrige validação de credenciais  |
| `chore`   | Tarefa de manutenção sem impacto funcional  | chore(env): atualiza arquivo .env            |
| `refactor`| Refatoração de código                       | refactor(topico-service): melhora leitura    |
| `docs`    | Alterações na documentação                  | docs(readme): adiciona instruções de setup   |
| `test`    | Inclusão ou modificação de testes           | test(api): adiciona teste de login           |
| `style`   | Ajustes de estilo                           | style(css): corrige indentação               |

---

## 📝 Formato da mensagem

Use sempre:

```bash
<tipo>(escopo): descrição curta e objetiva
```



### ✅ Exemplo ideal:

```bash
feat(schema-db): estrutura inicial do fórum com cursos, usuários, tópicos e respostas
```

- Use verbos no imperativo: "adiciona", "remove", "atualiza"
- Seja claro e direto: nada de “coisinhas” ou “mudei algo”
- Emojis são bem-vindos, desde que com moderação 😉
- Evite mensagens genéricas como: update, teste, changes

♻️ Recomendação bônus

Se possível, faça commits pequenos e frequentes. Assim o histórico fica mais fácil de entender, revisar e até reverter se preciso.