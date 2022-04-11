package io.rpg.model.object;

import io.rpg.model.data.Position;
import org.jetbrains.annotations.NotNull;

abstract public class InteractiveGameObject extends GameObject {
    public InteractiveGameObject(@NotNull String tag, @NotNull Position position) {
        super(tag, position, assetPath);
    }

    abstract public void onAction();
}
