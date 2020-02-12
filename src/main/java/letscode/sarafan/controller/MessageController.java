package letscode.sarafan.controller;

import letscode.sarafan.exceptions.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("message")
public class MessageController {
    private int counter = 4;
    private List<Map<String, String>> messages = new ArrayList<Map<String, String>>() {{
        add(new HashMap<String, String>() {{ put("id", "1"); put("text", "FirstMessage"); }});
        add(new HashMap<String, String>() {{ put("id", "2"); put("text", "SecondMessage"); }});
        add(new HashMap<String, String>() {{ put("id", "3"); put("text", "ThirdMessage"); }});
    }};

    @GetMapping
    public List<Map<String, String>> list() {
        return messages;
    }

    @GetMapping("{id}")
    public Map<String, String> getOne(@PathVariable String id) {
        return getMessage(id);
    }

    private Map<String, String> getMessage(@PathVariable String id) {
        return messages.stream().filter(message -> message.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    // fetch('/message', { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify( { text: 'Fourth message' } ) }).then(console.log)
    @PostMapping
    public Map<String, String> create(@RequestBody Map<String, String> message) {
        message.put("id", String.valueOf(counter++));
        messages.add(message);
        return message;
    }

    // fetch('/message/4', { method: 'PUT', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify( { text: 'Fourth message (4)', id: '10' } ) }).then(console.log)
    @PutMapping("{id}")
    public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> message) {
        Map<String, String> messageFromDb = getMessage(id);
        messageFromDb.putAll(message);
        messageFromDb.put("id", id);
        return messageFromDb;
    }

    // fetch('/message/4', { method: 'DELETE' }).then(console.log)
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        Map<String, String> message = getMessage(id);
        messages.remove(message);
    }
}
