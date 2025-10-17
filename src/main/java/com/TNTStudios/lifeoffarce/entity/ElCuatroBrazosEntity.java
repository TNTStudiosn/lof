// src/main/java/com/TNTStudios/lifeoffarce/entity/ElCuatroBrazosEntity.java
package com.TNTStudios.lifeoffarce.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
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
import net.minecraft.sounds.SoundEvent;

public class ElCuatroBrazosEntity extends Monster implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public ElCuatroBrazosEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    // Atributos definidos según tu petición
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 40.0D) // 40 HP
                .add(Attributes.MOVEMENT_SPEED, 0.28D) // Velocidad base
                .add(Attributes.ATTACK_DAMAGE, 6.0D) // 6 de daño
                .add(Attributes.FOLLOW_RANGE, 35.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        // La velocidad del ataque es 1.0, como pediste
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    // --- ANIMACIONES ---
    private static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");
    private static final RawAnimation WALK_ANIM = RawAnimation.begin().thenLoop("run");
    private static final RawAnimation RUN_ANIM = RawAnimation.begin().thenLoop("run"); // Añadida animación 'run'
    private static final RawAnimation ATTACK_ANIM = RawAnimation.begin().thenPlay("attack");

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "locomotion_controller", 5, this::locomotionPredicate));
        controllers.add(new AnimationController<>(this, "attack_controller", 0, this::attackPredicate));
    }

    private <E extends GeoEntity> PlayState locomotionPredicate(AnimationState<E> event) {
        // Para implementar 'run', necesitarías una lógica más compleja que verifique la velocidad.
        // Por ahora, usaremos 'walk' cuando se mueva.
        if (event.isMoving()) {
            event.getController().setAnimation(WALK_ANIM);
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(IDLE_ANIM);
        return PlayState.CONTINUE;
    }

    private <E extends GeoEntity> PlayState attackPredicate(AnimationState<E> event) {
        if (this.swinging) {
            event.getController().setAnimation(ATTACK_ANIM);
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    // --- SONIDOS ---
// Propuesta: Sonidos de Bestia Bruta (Ravager / Piglin Brute)

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        // Sonido de pasos pesados, como una bestia grande.
        this.playSound(SoundEvents.RAVAGER_STEP, 0.25F, 1.0F);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        // Un gruñido grave y amenazante de vez en cuando.
        return SoundEvents.PIGLIN_BRUTE_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        // Un rugido de dolor y furia.
        return SoundEvents.RAVAGER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        // Un último estertor de bestia derrotada.
        return SoundEvents.PIGLIN_BRUTE_DEATH;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}