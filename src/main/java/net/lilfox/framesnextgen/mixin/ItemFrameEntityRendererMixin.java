package net.lilfox.framesnextgen.mixin;

import net.lilfox.framesnextgen.configs.Configs;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.ItemFrameEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.Objects;

@Mixin(value = {ItemFrameEntityRenderer.class})
public class ItemFrameEntityRendererMixin {
    @Unique
    private ItemFrameEntity itemFrameEntity;

    @ModifyArgs(method = "render*", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;translate(FFF)V"), slice = @Slice(
            from = @At(value = "INVOKE", target = "net/minecraft/entity/decoration/ItemFrameEntity.getMapId()Ljava/util/OptionalInt;"),
            to = @At(value = "INVOKE", target = "java/util/OptionalInt.isPresent()Z")))
    private void setOffset(Args args){

        ItemStack heldItem = itemFrameEntity.getHeldItemStack();

        if(heldItem != null){
            if(Configs.noInvisibleFramesOffset)
                args.set(2, 0.4375f);

            if(Configs.customInvisibleFramesOffset) {


                NbtCompound nbt = heldItem.getSubNbt("display");
                if(nbt != null) {
                    NbtElement name = nbt.get("Name");
                    if (name != null && name.toString().matches("'\\{\"text\":\"ofs_(-?[01]\\.?[0-9]{1,6}|c)(_((-?[01]\\.?[0-9]{1,6})|c)){0,2}_\"}'")) {
                        String[] offsets = name.toString().split("_");

                        double [] cfgOffsets = {
                                Configs.invisibleFramesXOffset,
                                Configs.invisibleFramesYOffset,
                                Configs.invisibleFramesZOffset};

                        for(int i=1; i < offsets.length - 1; i++){
                            float offset = Objects.equals(offsets[i], "c") ? (float) cfgOffsets[i - 1] : Float.parseFloat(offsets[i]);
                            args.set(i - 1, offset);
                        }

                    }
                }
            }

        }
    }



    @Inject(method = "render*", at = @At(value = "HEAD"))
    private void disableItemFrameFrameRendering(ItemFrameEntity itemFrameEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        this.itemFrameEntity = itemFrameEntity;
    }

    @ModifyVariable(method = "render*", at = @At("STORE"))
    private boolean disableItemFrameFrameRendering(boolean bl) {
        return Configs.invisibleItemFrames && !this.itemFrameEntity.getHeldItemStack().isEmpty() || this.itemFrameEntity.isInvisible();
    }

}