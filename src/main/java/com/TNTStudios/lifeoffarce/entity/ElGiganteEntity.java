// src/main/java/com/TNTStudios/lifeoffarce/entity/ElGiganteEntity.java
package com.TNTStudios.lifeoffarce.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ElGiganteEntity extends Monster implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public ElGiganteEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.FOLLOW_RANGE, 35.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    // --- ANIMACIONES ---
    private static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");
    private static final RawAnimation WALK_ANIM = RawAnimation.begin().thenLoop("walk");
    private static final RawAnimation ATTACK_ANIM = RawAnimation.begin().thenPlay("attack");

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "locomotion_controller", 5, this::locomotionPredicate));
        controllers.add(new AnimationController<>(this, "attack_controller", 0, this::attackPredicate));
    }

    // Predicado para el movimiento (caminar/estar quieto)
    private <E extends GeoEntity> PlayState locomotionPredicate(AnimationState<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(WALK_ANIM);
            return PlayState.CONTINUE;
        }
        // Siempre queremos que una animación se esté reproduciendo en este controlador.
        event.getController().setAnimation(IDLE_ANIM);
        return PlayState.CONTINUE;
    }

    // Predicado solo para el ataque
    private <E extends GeoEntity> PlayState attackPredicate(AnimationState<E> event) {
        // La variable 'swinging' de la entidad vanilla nos dice cuándo está atacando.
        if (this.swinging) {
            event.getController().setAnimation(ATTACK_ANIM);
            return PlayState.CONTINUE;
        }

        // MEJORA 2: Si no está atacando, detenemos este controlador.
        // Esto permite que el controlador de locomoción tenga control total sobre el modelo.
        return PlayState.STOP;
    }


    // --- MECÁNICA DE MORIR DE DÍA ---
    @Override
    public void aiStep() {
        if (this.isAlive()) {
            boolean isDay = this.level().isDay() && !this.level().isClientSide();
            if (isDay) {
                float brightness = this.getLightLevelDependentMagicValue();
                BlockPos pos = this.blockPosition();
                if (brightness > 0.5F && this.level().canSeeSky(pos)) {
                    this.setSecondsOnFire(8);
                }
            }
        }
        super.aiStep();
    }

    // --- SONIDOS ---
    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.IRON_GOLEM_STEP, 0.15F, 1.0F);
    }

    @Override
    protected void playHurtSound(DamageSource source) {
        this.playSound(SoundEvents.IRON_GOLEM_HURT, 1.0F, 1.0F);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}