package runtime;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Parser {

    List<Cell> parse(BufferedReader reader) throws IOException {
        Optional<Cell> statement = this.parseCell(reader);
        var statements = new ArrayList<Cell>();
        while (statement.isPresent()) {
            statements.add(statement.get());
            statement = this.parseCell(reader);
        }
        return statements;
    }

    Optional<Cell> parseCell(BufferedReader reader) throws IOException {
        var line = reader.readLine();
        if (line == null) return Optional.empty();
        line = line.stripLeading();
        if (line.length() == 0) return this.parseCell(reader);
        if (line.startsWith("cons")) {
            return Optional.ofNullable(this.parseCons(reader));
        } else if (line.startsWith("node")) {
            return Optional.ofNullable(this.parseNode(line, reader));
        } else if (line.startsWith("leaf")) {
            return Optional.ofNullable(this.parseLeaf(line));
        }
        return Optional.empty();
    }

    Cell parseCons(BufferedReader reader) throws IOException {
        return new Cell(this.parseCell(reader).get(), this.parseCell(reader).get(), Cell.Kind.CONS);
    }

    Cell parseNode(String nodeString, BufferedReader reader) throws IOException {
        var nodes = nodeString.split("\\(");
        return new Cell(nodes[1].strip(), this.parseCell(reader).get(), Cell.Kind.NODE);
    }

    Cell parseLeaf(String leafString) throws IOException {
        var leaf = leafString.substring(leafString.indexOf('(')+1, leafString.indexOf(')'));
        var each = leaf.split(" ");
        return new Cell(each[0], each[1], Cell.Kind.LEAF);
    }
}