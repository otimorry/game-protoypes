package proto.tdg.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

import static proto.tdg.game.WorldState.keys;

/**
 * Created by Olva on 7/9/16.
 */
public class MainInputProcessor implements InputProcessor {
    /**
     * Handles raw user inputs and store them in the InputComponent
     */
    @Override
    public boolean keyDown(int keycode) {
        keys[keycode] = true;
        return true;
    }

    /**
     * Handles raw user inputs and store them in the InputComponent
     */
    @Override
    public boolean keyUp(int keycode) {
        keys[keycode] = false;

        if(keycode == Input.Keys.SPACE ) {
            CombatDialogUtil.DisplayMenu(true);
        }
        else if(keycode == Input.Keys.BACKSPACE) {
            CombatDialogUtil.DisplayMenu(false);
        }
        else {
            InputUtil.needAction = true;
        }

        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        keys[button] = true;

        InputUtil.reset();

        InputUtil.screenStartPt = new Vector2(screenX,screenY);
        InputUtil.worldStartPt = InputUtil.GetWorldPtFromScreenPt(InputUtil.screenStartPt);

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        keys[button] = false;

        InputUtil.screenEndPt = new Vector2(screenX,screenY);
        InputUtil.worldEndPt = InputUtil.GetWorldPtFromScreenPt(InputUtil.screenEndPt);


        if(button == Input.Buttons.LEFT) {
            InputUtil.isLeftClick = true;

            if(!CooldownUtil.LeftClickDoubleTapped.isOnCooldown()) {
                CooldownUtil.LeftClickDoubleTapped.startCooldown();
            }
            else {
                System.out.println("left double tapped");
                InputUtil.isLeftDoubleClick = true;
            }
        }
        else if(button == Input.Buttons.RIGHT) {
            InputUtil.isRightClick = true;
        }

        InputUtil.needAction = true;

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        boolean drag = false;

        if(keys[Input.Buttons.LEFT])
        {
            InputUtil.screenDragPt = new Vector2(screenX,screenY);
            InputUtil.worldDragPt = InputUtil.GetWorldPtFromScreenPt(InputUtil.screenDragPt);

            InputUtil.calcDragBound();
            drag = InputUtil.isDrag = true;
        }

        return drag;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        //System.out.println(String.format("Mouse-moved -- ScreenX: %s ScreenY: %s",screenX, screenY));
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        // TODO Auto-generated method stub
        return false;
    }
}
