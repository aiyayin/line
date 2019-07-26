package line.entity;

import androidx.annotation.DrawableRes;

public class ActivityItem {
    public ActivityItem(String name, int icon, Class targetActivity) {
        this.name = name;
        this.icon = icon;
        this.targetActivity = targetActivity;
    }

    public String name;
    public @DrawableRes
    int icon;
    public Class targetActivity;
}