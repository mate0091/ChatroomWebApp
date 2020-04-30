package HTMLgenerators.ShowPage;

import Models.Message;

import java.util.List;

public class MessageShowPageGenerator extends ShowPageGenerator<Message>
{
    public MessageShowPageGenerator(List<Message> list) {
        super(list);
    }
}
