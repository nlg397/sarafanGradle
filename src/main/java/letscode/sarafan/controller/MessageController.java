package letscode.sarafan.controller;

import letscode.sarafan.domain.Message;
import letscode.sarafan.repo.MessageRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("message")
public class MessageController {
    private final MessageRepo messageRepo;

    @Autowired
    public MessageController(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @GetMapping
    public List<Message> list() {
        return messageRepo.findAll();
    }

    @GetMapping("{id}")
    public Message getOne(@PathVariable("id") Message message) {
        return message;
    }

    // fetch('/message', { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify( { text: 'Fourth message' } ) }).then(console.log)
    @PostMapping
    public Message create(@RequestBody Message message) {
        return messageRepo.save(message);
    }

    // fetch('/message/4', { method: 'PUT', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify( { text: 'Fourth message (4)', id: '10' } ) }).then(console.log)
    @PutMapping("{id}")
    public Message update(@PathVariable("id") Message messageFromDb,
                                      @RequestBody Message message) {
        BeanUtils.copyProperties(message, messageFromDb, "id");
        return messageRepo.save(messageFromDb);
    }

    // fetch('/message/4', { method: 'DELETE' }).then(console.log)
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Message message) {
        messageRepo.delete(message);
    }
}
