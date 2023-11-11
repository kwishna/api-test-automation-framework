package rest.cucumber;

import rest.cucumber.utils.Replacer;

import java.util.HashMap;
import java.util.Map;

public class Rough {

    public static void main(String[] args) {

        String books = """
                <response version-api="2.0">
                        <value>
                            <books>
                                <book available="20" id="1">
                                    <title>${animal}</title>
                                    <author id="1">Miguel de Cervantes</author>
                                </book>
                                <book available="14" id="2">
                                    <title>${fruit}</title>
                                   <author id="2">JD Salinger</author>
                               </book>
                               <book available="13" id="3">
                                   <title>${country}</title>
                                   <author id="3">Lewis Carroll</author>
                               </book>
                               <book available="5" id="4">
                                   <title>${os}</title>
                                   <author id="4">Miguel de Cervantes</author>
                               </book>
                           </books>
                       </value>
                </response>
                       """;

        Map<String, String> _map = new HashMap<>();
        _map.put("animal", "Cat");
        _map.put("fruit", "Apple");
        _map.put("country", "India");
        _map.put("os", "Windows");

        String _result = Replacer.induceParameters(books, _map);
        System.out.println(_result);
    }
}
