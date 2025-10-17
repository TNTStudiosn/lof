// src/main/java/com/TNTStudios/lifeoffarce/entity/MayaMaskaEntity.java
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

public class MayaMaskaEntity extends Monster implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public MayaMaskaEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    // He definido los atributos como pediste: 40 de vida y una velocidad de movimiento lenta.
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 40.0D) // 40 HP
                .add(Attributes.MOVEMENT_SPEED, 0.20D) // Lento, como un gusano gigante
                .add(Attributes.ATTACK_DAMAGE, 7.0D) // Un daño respetable
                .add(Attributes.FOLLOW_RANGE, 35.0D); // Rango de seguimiento estándar
    }

    @Override
    protected void registerGoals() {
        // La IA es similar a las otras entidades para mantener consistencia.
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    // --- ANIMACIONES ---
    // Defino las animaciones que me indicaste.
    private static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");
    private static final RawAnimation WALK_ANIM = RawAnimation.begin().thenLoop("walk");
    private static final RawAnimation ATTACK_ANIM = RawAnimation.begin().thenPlay("attack");

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "locomotion_controller", 5, this::locomotionPredicate));
        controllers.add(new AnimationController<>(this, "attack_controller", 0, this::attackPredicate));
    }

    // Este predicado controla las animaciones de movimiento (idle/walk).
    private <E extends GeoEntity> PlayState locomotionPredicate(AnimationState<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(WALK_ANIM);
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(IDLE_ANIM);
        return PlayState.CONTINUE;
    }

    // Este predicado se encarga únicamente de la animación de ataque.
    private <E extends GeoEntity> PlayState attackPredicate(AnimationState<E> event) {
        if (this.swinging) {
            event.getController().setAnimation(ATTACK_ANIM);
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    // --- SONIDOS ---
    // He usado sonidos de Enderman como placeholder, que creo que le van bien a un gusano gigante y raro.
    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENDERMAN_HURT, 0.15F, 1.0F);
    }

    @Override
    protected void playHurtSound(DamageSource source) {
        this.playSound(SoundEvents.ENDERMAN_HURT, 1.0F, 1.0F);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}