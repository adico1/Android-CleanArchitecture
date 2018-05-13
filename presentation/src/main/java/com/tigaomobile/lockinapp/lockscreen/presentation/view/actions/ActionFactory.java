package com.tigaomobile.lockinapp.lockscreen.presentation.view.actions;

/**
 * Created by adi on 10/04/2018.
 * Description: ActionFactory is implemented as infrastructure for dynamic buttons implementation
 * ActionFactory gives the ability to generate a unique name to link the buttons to actions
 *
 * Use cases:
 * - ButtonGroup draws a set of drag target buttons, on drag, on one of the buttons, an action
 *   is triggered. The buttons and related actions are configured in a config file,
 *   to link a button to an action, we need to dynamically generate a link from button id (int) to
 *   action (IAction). button ids are generated dynamically by the view, so we need some qualifier
 */

import android.content.Context;
import java.util.HashMap;

public class ActionFactory {
    private Context _context;

    public ActionFactory(Context context) {
        _context = context;
    }

    private static HashMap<String, IAction> commands = null;

    public IAction create(String command, String url) {
        if(commands == null) {
            commands = new HashMap<>();
            commands.put("camera", new CameraAction(_context));
            commands.put("whatsapp", new WhatsappAction(_context));
            commands.put("phone", new PhoneAction(_context));
            commands.put("facebook", new FacebookAction(_context));
            commands.put("chrome", new ChromeAction(_context));
            commands.put("unlock", new NoopAction(_context));
        }

        return commands.get(command);
    }
}
