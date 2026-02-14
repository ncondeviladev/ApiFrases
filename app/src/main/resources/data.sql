-- Categorías (3 Mínimo)
INSERT INTO
    categoria (nombre)
VALUES ('Ciencia')
ON CONFLICT DO NOTHING;

INSERT INTO
    categoria (nombre)
VALUES ('Literatura')
ON CONFLICT DO NOTHING;

INSERT INTO
    categoria (nombre)
VALUES ('Filosofía')
ON CONFLICT DO NOTHING;

INSERT INTO
    categoria (nombre)
VALUES ('Cine')
ON CONFLICT DO NOTHING;
-- NUEVA

-- Autores (Películas y Personas)
-- Personas reales
INSERT INTO
    autor (
        nombre,
        anio_nacimiento,
        anio_fallecimiento,
        profesion
    )
VALUES (
        'Albert Einstein',
        1879,
        1955,
        'Físico teórico'
    )
ON CONFLICT DO NOTHING;

INSERT INTO
    autor (
        nombre,
        anio_nacimiento,
        anio_fallecimiento,
        profesion
    )
VALUES (
        'Miguel de Cervantes',
        1547,
        1616,
        'Escritor'
    )
ON CONFLICT DO NOTHING;

INSERT INTO
    autor (
        nombre,
        anio_nacimiento,
        anio_fallecimiento,
        profesion
    )
VALUES (
        'Sócrates',
        -470,
        -399,
        'Filósofo'
    )
ON CONFLICT DO NOTHING;

INSERT INTO
    autor (
        nombre,
        anio_nacimiento,
        anio_fallecimiento,
        profesion
    )
VALUES (
        'Marie Curie',
        1867,
        1934,
        'Científica'
    )
ON CONFLICT DO NOTHING;

INSERT INTO
    autor (
        nombre,
        anio_nacimiento,
        anio_fallecimiento,
        profesion
    )
VALUES (
        'Oscar Wilde',
        1854,
        1900,
        'Escritor y poeta'
    )
ON CONFLICT DO NOTHING;

INSERT INTO
    autor (
        nombre,
        anio_nacimiento,
        anio_fallecimiento,
        profesion
    )
VALUES (
        'Aristóteles',
        -384,
        -322,
        'Filósofo y científico'
    )
ON CONFLICT DO NOTHING;

-- Películas (Tratadas como Autores para el ejemplo)
INSERT INTO
    autor (
        nombre,
        anio_nacimiento,
        anio_fallecimiento,
        profesion
    )
VALUES (
        'El Padrino',
        1972,
        NULL,
        'Película'
    )
ON CONFLICT DO NOTHING;

INSERT INTO
    autor (
        nombre,
        anio_nacimiento,
        anio_fallecimiento,
        profesion
    )
VALUES (
        'Star Wars: El Imperio Contraataca',
        1980,
        NULL,
        'Película'
    )
ON CONFLICT DO NOTHING;

INSERT INTO
    autor (
        nombre,
        anio_nacimiento,
        anio_fallecimiento,
        profesion
    )
VALUES (
        'Forrest Gump',
        1994,
        NULL,
        'Película'
    )
ON CONFLICT DO NOTHING;

INSERT INTO
    autor (
        nombre,
        anio_nacimiento,
        anio_fallecimiento,
        profesion
    )
VALUES (
        'Terminator 2',
        1991,
        NULL,
        'Película'
    )
ON CONFLICT DO NOTHING;

-- Frases
-- IDs asumidos: 1=Ciencia, 2=Literatura, 3=Filosofía, 4=Cine
-- IDs Autores 1..6 (Personas), 7..10 (Películas)

-- Frase para HOY (Prueba /frases/dia)
INSERT INTO
    frase (
        texto,
        fecha_programada,
        autor_id,
        categoria_id
    )
VALUES (
        'La imaginación es más importante que el conocimiento.',
        CURRENT_DATE,
        1,
        1
    );

-- Frase para MAÑANA (Prueba /frases/dia?fecha=...)
INSERT INTO
    frase (
        texto,
        fecha_programada,
        autor_id,
        categoria_id
    )
VALUES (
        'En un lugar de la Mancha, de cuyo nombre no quiero acordarme...',
        CURRENT_DATE + 1,
        2,
        2
    );

-- Frases Cine
INSERT INTO
    frase (
        texto,
        fecha_programada,
        autor_id,
        categoria_id
    )
VALUES (
        'Le haré una oferta que no podrá rechazar.',
        NULL,
        7,
        4
    );

INSERT INTO
    frase (
        texto,
        fecha_programada,
        autor_id,
        categoria_id
    )
VALUES (
        'Yo soy tu padre.',
        NULL,
        8,
        4
    );
-- Star Wars
INSERT INTO
    frase (
        texto,
        fecha_programada,
        autor_id,
        categoria_id
    )
VALUES (
        'La vida es como una caja de bombones, nunca sabes lo que te va a tocar.',
        NULL,
        9,
        4
    );
-- Forrest Gump
INSERT INTO
    frase (
        texto,
        fecha_programada,
        autor_id,
        categoria_id
    )
VALUES (
        'Hasta la vista, baby.',
        NULL,
        10,
        4
    );
-- Terminator

-- Resto de frases
INSERT INTO
    frase (
        texto,
        fecha_programada,
        autor_id,
        categoria_id
    )
VALUES (
        'Yo solo sé que no sé nada.',
        NULL,
        3,
        3
    );

INSERT INTO
    frase (
        texto,
        fecha_programada,
        autor_id,
        categoria_id
    )
VALUES (
        'Nada en la vida es para ser temido, es solo para ser comprendido.',
        NULL,
        4,
        1
    );

INSERT INTO
    frase (
        texto,
        fecha_programada,
        autor_id,
        categoria_id
    )
VALUES (
        'Sé tú mismo, el resto de los papeles ya están cogidos.',
        NULL,
        5,
        2
    );

INSERT INTO
    frase (
        texto,
        fecha_programada,
        autor_id,
        categoria_id
    )
VALUES (
        'La inteligencia consiste no solo en el conocimiento, sino también en la destreza de aplicar los conocimientos en la práctica.',
        NULL,
        6,
        3
    );

INSERT INTO
    frase (
        texto,
        fecha_programada,
        autor_id,
        categoria_id
    )
VALUES (
        'La vida es muy peligrosa. No por las personas que hacen el mal, sino por las que se sientan a ver lo que pasa.',
        NULL,
        1,
        1
    );

INSERT INTO
    frase (
        texto,
        fecha_programada,
        autor_id,
        categoria_id
    )
VALUES (
        'El que lee mucho y anda mucho, ve mucho y sabe mucho.',
        NULL,
        2,
        2
    );

INSERT INTO
    frase (
        texto,
        fecha_programada,
        autor_id,
        categoria_id
    )
VALUES (
        'Conócete a ti mismo.',
        NULL,
        3,
        3
    );

-- Frase con fecha fija pasada (prueba query por fecha manual)
INSERT INTO
    frase (
        texto,
        fecha_programada,
        autor_id,
        categoria_id
    )
VALUES (
        'Mejor es prevenir que curar.',
        '2000-01-01',
        4,
        1
    );

INSERT INTO
    frase (
        texto,
        fecha_programada,
        autor_id,
        categoria_id
    )
VALUES (
        'Vivir es lo más raro de este mundo, la mayoría de la gente existe, eso es todo.',
        NULL,
        5,
        2
    );

INSERT INTO
    frase (
        texto,
        fecha_programada,
        autor_id,
        categoria_id
    )
VALUES (
        'Somos lo que hacemos día a día. De modo que la excelencia no es un acto, sino un hábito.',
        NULL,
        6,
        3
    );

-- Usuarios (Pass: 1234 cifrada con BCrypt)
INSERT INTO
    usuario (username, password, rol)
VALUES (
        'admin',
        '$2a$10$l.XJR8lX6Y9hBTyMDs9w..QFvulNnT1xQv5vWqt01Yr.S2bcamY/i',
        'ADMIN'
    )
ON CONFLICT DO NOTHING;

INSERT INTO
    usuario (username, password, rol)
VALUES (
        'user',
        '$2a$10$l.XJR8lX6Y9hBTyMDs9w..QFvulNnT1xQv5vWqt01Yr.S2bcamY/i',
        'STANDARD'
    )
ON CONFLICT DO NOTHING;