package de.kilobyte22.binaryformat;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class StringTag extends Tag {
    private String value;

    public StringTag() {
        this("");
    }

    public StringTag(String value) {
        this.value = value;
    }

    @Override
    public void load(DataInput input) throws IOException {

        value = input.readUTF();
    }

    @Override
    public void save(DataOutput output) throws IOException {
        output.writeUTF(value);
    }

    @Override
    protected TagType getType() {
        return TagType.TAG_STRING;
    }

    public String value() {
        return value;
    }
}
