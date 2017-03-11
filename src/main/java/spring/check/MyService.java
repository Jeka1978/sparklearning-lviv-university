package spring.check;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Evegeny on 11/03/2017.
 */
@Component
public class MyService {
    @Autowired
    private MyService mySelfProxy;

    public void a(){
        mySelfProxy.b();
    }
    @ProstoTak
    public void b(){
        System.out.println("BBBBBBBBBBB");
    }
}
