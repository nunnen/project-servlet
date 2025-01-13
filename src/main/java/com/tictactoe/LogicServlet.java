package com.tictactoe;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "LogicServlet", urlPatterns = "/logic")
public class LogicServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        Field field = extractField(session);

        // получаем номер клетки
        int index = getSelectedIndex(req);
        // получаем знак в текущей клетке
        Sign currentSign = field.getFieldData().get(index);

        // если клетка не пустая, то возвращаем пользователя на текущую страницу
        // без изменений
        if (currentSign != Sign.EMPTY) {
            req.getRequestDispatcher("logic.jsp").forward(req, resp);
            return;
        }

        // иначе ставим крестик в клетке
        field.getField().put(index, Sign.CROSS);
        if (checkWin(session, resp, field)) {
            return;
        }

        // получаем индекс поля с пустой клеткой
        // и ставим туда ноль (AI)
        int emptyFieldIndex = field.getEmptyFieldIndex();
        if (emptyFieldIndex >= 0) {
            field.getField().put(emptyFieldIndex, Sign.NOUGHT);
            if (checkWin(session, resp, field)) {
                return;
            }
        }

        // обработка ничьи
        else {
            // флаг для ничьи
            session.setAttribute("draw", true);

            List<Sign> data = field.getFieldData();
            session.setAttribute("data", data);

            resp.sendRedirect("/index.jsp");
            return;
        }

        // список знаков по индексам полей
        List<Sign> data = field.getFieldData();

        // отправляем данные в сессию
        session.setAttribute("data", data);
        session.setAttribute("field", field);

        resp.sendRedirect("/index.jsp");
    }

    private int getSelectedIndex(HttpServletRequest request) {
        String onClick = request.getParameter("click");
        boolean isNumeric = onClick.matches("[0-9]*");
        return isNumeric ? Integer.parseInt(onClick) : 0;
    }

    private Field extractField(HttpSession session) {
        Object fieldAttribute = session.getAttribute("field");
        if (Field.class != fieldAttribute.getClass()) {
            session.invalidate();
            throw new RuntimeException("Invalid field attribute");
        }
        return (Field) fieldAttribute;
    }

    private boolean checkWin(HttpSession session, HttpServletResponse response, Field field) throws IOException {
        Sign winner = field.checkWin();
        if (winner == Sign.CROSS || winner == Sign.NOUGHT) {
            // добавляем флаг, что есть победитель
            session.setAttribute("winner", winner);
            // получаем список значений поля
            List<Sign> data = field.getFieldData();
            // обновляем этот список и отправляем на сессию
            session.setAttribute("data", data);
            // шлем редирект
            response.sendRedirect("/index.jsp");
            return true;
        }
        return false;
    }
}
