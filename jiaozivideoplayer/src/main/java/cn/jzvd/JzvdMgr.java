package cn.jzvd;

/**
 * Put JZVideoPlayer into layout
 * From a JZVideoPlayer to another JZVideoPlayer
 * Created by Nathen on 16/7/26.
 */
public class JzvdMgr {

    public static com.ardakalan.muzikplayer.calisma.video.Jzvd FIRST_FLOOR_JZVD;
    public static com.ardakalan.muzikplayer.calisma.video.Jzvd SECOND_FLOOR_JZVD;

    public static com.ardakalan.muzikplayer.calisma.video.Jzvd getFirstFloor() {
        return FIRST_FLOOR_JZVD;
    }

    public static void setFirstFloor(com.ardakalan.muzikplayer.calisma.video.Jzvd jzvd) {
        FIRST_FLOOR_JZVD = jzvd;
    }

    public static com.ardakalan.muzikplayer.calisma.video.Jzvd getSecondFloor() {
        return SECOND_FLOOR_JZVD;
    }

    public static void setSecondFloor(com.ardakalan.muzikplayer.calisma.video.Jzvd jzvd) {
        SECOND_FLOOR_JZVD = jzvd;
    }

    public static com.ardakalan.muzikplayer.calisma.video.Jzvd getCurrentJzvd() {
        if (getSecondFloor() != null) {
            return getSecondFloor();
        }
        return getFirstFloor();
    }

    public static void completeAll() {
        if (SECOND_FLOOR_JZVD != null) {
            SECOND_FLOOR_JZVD.onCompletion();
            SECOND_FLOOR_JZVD = null;
        }
        if (FIRST_FLOOR_JZVD != null) {
            FIRST_FLOOR_JZVD.onCompletion();
            FIRST_FLOOR_JZVD = null;
        }
    }
}
