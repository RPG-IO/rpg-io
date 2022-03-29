package io.rpg.model.object;

import io.rpg.model.data.Position;
import org.jetbrains.annotations.NotNull;

final public class CollectibleGameObject extends InteractiveGameObject {
    public CollectibleGameObject(@NotNull String tag, @NotNull Position position) {
        super(tag, position);
    }

    @Override
    public void onAction() {
        
    }
}