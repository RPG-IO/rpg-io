package io.rpg.model.object;

import io.rpg.model.data.Position;
import org.jetbrains.annotations.NotNull;

final public class NavigationalGameObject extends InteractiveGameObject {
    public NavigationalGameObject(@NotNull String tag, @NotNull Position position) {
        super(tag, position);
    }

    public void navigateTo(Object target){};

    @Override
    public void onAction() {

    }
}
