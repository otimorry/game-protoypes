package firstgen.hopelesswar.util;

import com.badlogic.ashley.core.ComponentMapper;
import firstgen.hopelesswar.model.*;

/**
 * Created by Olva on 7/9/16.
 */
public class CM {
    public static final ComponentMapper<CState> STATE = ComponentMapper.getFor(CState.class);
    public static final ComponentMapper<CVisual> VISUAL = ComponentMapper.getFor(CVisual.class);
    public static final ComponentMapper<CSelect> SELECT = ComponentMapper.getFor(CSelect.class);
    public static final ComponentMapper<CControllable> CONTROLLABLE = ComponentMapper.getFor(CControllable.class);
    public static final ComponentMapper<CCombat> COMBAT = ComponentMapper.getFor(CCombat.class);
    public static final ComponentMapper<CTarget> TARGET = ComponentMapper.getFor(CTarget.class);
    public static final ComponentMapper<CCommandListener> LISTENER = ComponentMapper.getFor(CCommandListener.class);
    public static final ComponentMapper<CCommandHandler> HANDLER = ComponentMapper.getFor(CCommandHandler.class);
    public static final ComponentMapper<CCommandSource> SOURCE = ComponentMapper.getFor(CCommandSource.class);
    public static final ComponentMapper<CStatus> STATUS = ComponentMapper.getFor(CStatus.class);
    public static final ComponentMapper<CSelectable> SELECTABLE = ComponentMapper.getFor(CSelectable.class);
    public static final ComponentMapper<COverlay> OVERLAY = ComponentMapper.getFor(COverlay.class);
}
