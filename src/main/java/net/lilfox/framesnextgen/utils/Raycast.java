package net.lilfox.framesnextgen.utils;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

public class Raycast {


    public static BlockHitResult raycastIgnoreNonCollisionBlocks(ClientPlayerEntity player, ClientWorld world){
        float f = player.getPitch();
        float g = player.getYaw();
        Vec3d vec3d = player.getEyePos();
        float h = MathHelper.cos(-g * 0.017453292F - 3.1415927F);
        float i = MathHelper.sin(-g * 0.017453292F - 3.1415927F);
        float j = -MathHelper.cos(-f * 0.017453292F);
        float k = MathHelper.sin(-f * 0.017453292F);
        float l = i * j;
        float n = h * j;
        double d = 5.0;
        Vec3d vec3d2 = vec3d.add((double)l * 5.0, (double)k * 5.0, (double)n * 5.0);

        return world.raycast(new RaycastContext(vec3d, vec3d2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.ANY, player));

    }

}
