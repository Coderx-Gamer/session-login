package net.sessionlogin.mixin.accessor;

import net.minecraft.client.util.Session;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Optional;

@Mixin(Session.class)
public interface SessionAccessor {
    @Accessor
    @Mutable
    void setAccessToken(String accessToken);

    @Accessor
    @Mutable
    void setUuid(String uuid);

    @Accessor
    @Mutable
    void setUsername(String username);

    @Accessor
    @Mutable
    void setAccountType(Session.AccountType accountType);

    @Accessor
    @Mutable
    void setXuid(Optional<String> xuid);

    @Accessor
    @Mutable
    void setClientId(Optional<String> clientId);
}
