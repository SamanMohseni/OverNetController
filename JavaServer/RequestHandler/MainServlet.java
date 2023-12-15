package RequestHandler;

import Database.Database;
import com.google.common.io.CharStreams;
import org.eclipse.jetty.http.HttpStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String body = CharStreams.toString(req.getReader());

        //messages from controllers:
        if (body.compareTo("what") == 0){
            resp.setStatus(HttpStatus.OK_200);
            resp.getWriter().write(Database.getInstance().getState());
        }
        else if (body.compareTo("on") == 0){
            Database.getInstance().setOn(true);
            Database.getInstance().setUpToDate(false);

            resp.setStatus(HttpStatus.OK_200);
            resp.getWriter().write(Database.getInstance().getState());
        }
        else if (body.compareTo("off") == 0){
            Database.getInstance().setOn(false);
            Database.getInstance().setUpToDate(false);

            resp.setStatus(HttpStatus.OK_200);
            resp.getWriter().write(Database.getInstance().getState());
        }
        //messages form pomp:
        else if (body.compareTo("p-what") == 0){

            if(Database.getInstance().isUpToDate()){ //nothing new, just waist some time to reduce internet cost:

                //new request might come here...
                for(int i=0; i<100; i++){
                    //check if there is any new request:
                    if(!Database.getInstance().isUpToDate()){
                        break;
                    }
                    try {
                        Thread.sleep(100);
                    }
                    catch (InterruptedException e){
                        Thread.interrupted();
                    }
                }
            }

            resp.setStatus(HttpStatus.OK_200);
            if(Database.getInstance().isChargeSent()){
                resp.getWriter().write(Database.getInstance().getState());
            }
            else{
                resp.getWriter().write(Database.getInstance().getState()+"-"+Database.getInstance().getCharge());
                Database.getInstance().setChargeSent(true);
            }

            Database.getInstance().setUpToDate(true);
        }
        else if (body.compareTo("p-on") == 0){
            Database.getInstance().setOn(true);
            Database.getInstance().setUpToDate(true);
            resp.setStatus(HttpStatus.OK_200);
            resp.getWriter().write(Database.getInstance().getState());
        }
        else if (body.compareTo("p-off") == 0){
            Database.getInstance().setOn(false);
            Database.getInstance().setUpToDate(true);
            resp.setStatus(HttpStatus.OK_200);
            resp.getWriter().write(Database.getInstance().getState());
        }
        //messages form hotspot:
        else if (body.compareTo("charge") == 0){
            Database.getInstance().setCharge(true);
            Database.getInstance().setUpToDate(false);
            Database.getInstance().setChargeSent(false);

            resp.setStatus(HttpStatus.OK_200);
            resp.getWriter().write("ok");
        }
        else if (body.compareTo("dis") == 0){
            Database.getInstance().setCharge(false);
            Database.getInstance().setUpToDate(false);
            Database.getInstance().setChargeSent(false);

            resp.setStatus(HttpStatus.OK_200);
            resp.getWriter().write("ok");
        }

        //invalid:
        else{
            resp.setStatus(HttpStatus.BAD_REQUEST_400);
            resp.getWriter().write("invalid command");
        }

    }
}
