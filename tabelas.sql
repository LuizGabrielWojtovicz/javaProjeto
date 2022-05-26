CREATE TABLE onibus (
  id   INTEGER NOT NULL,
  nome TEXT NOT NULL,
  PRIMARY KEY ("id" AUTOINCREMENT)
);

CREATE TABLE Horarios (
  id         INTEGER NOT NULL,
  horario    INTEGER NOT NULL,
  onibus_id  INTEGER NULL,
  dia        TEXT NOT NULL,

  PRIMARY KEY ("id" AUTOINCREMENT),
  FOREIGN KEY(onibus_id) REFERENCES Onibus(id)
);
