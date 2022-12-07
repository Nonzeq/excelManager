package com.kobylchak.core.exelservice.validators;

import javax.swing.*;
import java.io.File;

public interface Validator {
    void validate(File file, JFrame frame);
    boolean fileIsValid();
}
