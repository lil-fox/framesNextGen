package net.lilfox.framesnextgen.mixin;

import net.lilfox.framesnextgen.configs.Configs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.function.Predicate;

@Mixin(value = GameRenderer.class, priority = 1001)
public class GameRendererMixin {

    @Shadow
    @Final
    MinecraftClient client;
    @ModifyArg(method = "updateTargetedEntity",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/entity/projectile/ProjectileUtil;raycast(" +
                            "Lnet/minecraft/entity/Entity;" +
                            "Lnet/minecraft/util/math/Vec3d;" +
                            "Lnet/minecraft/util/math/Vec3d;" +
                            "Lnet/minecraft/util/math/Box;" +
                            "Ljava/util/function/Predicate;D)" +
                            "Lnet/minecraft/util/hit/EntityHitResult;"))
    private Predicate<Entity> overrideTargetedEntityCheck(Predicate<Entity> predicate)
    {

        if ((Configs.hangableEntitiesBypass && this.client.player != null
                && !this.client.player.isSneaking()))
        {
            predicate = predicate.and((entityIn) -> !(entityIn instanceof AbstractDecorationEntity));
        }

        return predicate;
    }
}
