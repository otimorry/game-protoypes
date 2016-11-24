package firstgen.hopelesswar.util;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olva on 7/24/16.
 */
public class GameViewHandler extends BaseViewHandler {
    public GameViewHandler(Engine engine) {
        super(engine);
    }

    @Override
    public void handle() {
        if(InputUtil.needAction) {
            List<Entity> eList = new ArrayList<>();

            if (InputUtil.isLeftClick && !InputUtil.isLeftDoubleClick && !InputUtil.isDrag) {
                Entity selected = InputUtil.GetSelectableEntityAtPoint(InputUtil.worldStartPt);
                if(selected != null) {
                    eList.add(selected);
                }
                handleSelect(eList, Enums.Target.SINGLE);
            }
            else if (InputUtil.isLeftDoubleClick && !InputUtil.isDrag ) {
                Entity selected = InputUtil.GetSelectableEntityAtPoint(InputUtil.worldStartPt);
                Enums.UnitType unitType;

                if(selected != null) {
                    unitType = CM.STATE.get(selected).unitType;
                    eList.addAll(InputUtil.GetEntitiesOfSameUnitType(unitType));
                }
                handleSelect(eList, Enums.Target.MULTIPLE);
            }
            else if (InputUtil.isDrag) {
                eList.addAll(InputUtil.GetOverlappedEntities(InputUtil.NormalizedDragBound));
                handleSelect(eList, Enums.Target.MULTIPLE);
            }
            else if (InputUtil.isRightClick) {
                handleAction();
            }

            InputUtil.needAction = false;
            InputUtil.isDrag = false;
        }
    }
}
