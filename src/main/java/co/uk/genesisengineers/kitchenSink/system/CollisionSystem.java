package co.uk.genesisengineers.kitchenSink.system;

import co.uk.genesisengineers.core.entity.Entity;
import co.uk.genesisengineers.core.entity.EntityHandler;
import co.uk.genesisengineers.core.entity.component.ComponentBase;
import co.uk.genesisengineers.kitchenSink.entityComponent.Collision;
import co.uk.genesisengineers.kitchenSink.entityComponent.Movement;
import co.uk.genesisengineers.kitchenSink.entityComponent.Position;
import co.uk.genesisengineers.core.system.SystemBase;
import co.uk.genesisengineers.core.util.CollisionBox;

import java.util.ArrayList;

public class CollisionSystem extends SystemBase {

    private ArrayList<Entity> entityList = new ArrayList<>();

    public CollisionSystem () {
        this.type = Type.COLLISION;
    }

    @Override
    public void init (EntityHandler entityHandler) {
        for (Entity entity : entityHandler) {
            if (entity.hasComponent(ComponentBase.Type.POSITION) && entity.hasComponent(ComponentBase.Type.COLLISION)) {
                entityList.add(entity);
            }
        }
    }

    @Override
    public void update () {
        Position position;
        Collision collision;

        for (Entity entity : entityList) {
            position = (Position) entity.getComponent(ComponentBase.Type.POSITION);
            collision = (Collision) entity.getComponent(ComponentBase.Type.COLLISION);

            if (position == null || collision == null) {
                continue;
            }

            CollisionBox entityCollisionBox = new CollisionBox();
            entityCollisionBox.init(position.getCoordinates(), collision.getHalfDimensions());
            if (objectCollision(entity, entityCollisionBox) == true) {
                Movement movement = (Movement) entity.getComponent(ComponentBase.Type.MOVEMENT);
                if (movement != null) {
                    movement.resetPosition();
                }
            }
        }
    }

    private boolean objectCollision (Entity entity, CollisionBox entityCollisionBox) {
        Position position;
        Collision collision;

        CollisionBox secondCollisionBox = new CollisionBox();

        for (Entity secondEntity : entityList) {
            if (entity.getId() == secondEntity.getId()) {
                continue;
            }
            position = (Position) secondEntity.getComponent(ComponentBase.Type.POSITION);
            collision = (Collision) secondEntity.getComponent(ComponentBase.Type.COLLISION);
            if (position == null || collision == null) {
                continue;
            }

            secondCollisionBox.init(position.getCoordinates(), collision.getHalfDimensions());
            if (entityCollisionBox.boxCollisionTest(secondCollisionBox) == true) {
                return true;
            }
        }
        return false;
    }
}
