package com.techzealot.designpatterns.structural.composite;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.text.MessageFormat;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class Leaf extends Component {

    private String name;

    @Override
    public void printStructure(String prefix) {
        System.out.println(MessageFormat.format("{0}-{1}", prefix, name));
    }
}
