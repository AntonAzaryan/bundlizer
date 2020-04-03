package dev.ahmedmourad.bundlizer

import android.os.Bundle
import kotlinx.serialization.CompositeEncoder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialDescriptor
import kotlinx.serialization.builtins.AbstractEncoder

internal class BundleEncoder(
    private val bundle: Bundle,
    private val parentBundle: Bundle? = null,
    private val keyInParent: String? = null,
    private val isInitializer: Boolean = true
) : AbstractEncoder() {

    private var key: String = ""

    override fun encodeElement(descriptor: SerialDescriptor, index: Int): Boolean {
        this.key = descriptor.getElementName(index)
        return super.encodeElement(descriptor, index)
    }

    override fun beginStructure(
        descriptor: SerialDescriptor,
        vararg typeSerializers: KSerializer<*>
    ): CompositeEncoder {
        return if (isInitializer) {
            BundleEncoder(
                bundle,
                null,
                key,
                false
            )
        } else {
            BundleEncoder(
                Bundle(),
                bundle,
                key,
                false
            )
        }
    }

    override fun endStructure(descriptor: SerialDescriptor) {
        keyInParent?.let {
            parentBundle?.putBundle(it, bundle)
        }
    }

    override fun encodeBoolean(value: Boolean) {
        bundle.putBoolean(key, value)
    }

    override fun encodeByte(value: Byte) {
        bundle.putByte(key, value)
    }

    override fun encodeChar(value: Char) {
        bundle.putChar(key, value)
    }

    override fun encodeDouble(value: Double) {
        bundle.putDouble(key, value)
    }

    override fun encodeEnum(enumDescriptor: SerialDescriptor, index: Int) {
        bundle.putInt(key, index)
    }

    override fun encodeFloat(value: Float) {
        bundle.putFloat(key, value)
    }

    override fun encodeInt(value: Int) {
        bundle.putInt(key, value)
    }

    override fun encodeLong(value: Long) {
        bundle.putLong(key, value)
    }

    override fun encodeNull() {

    }

    override fun encodeShort(value: Short) {
        bundle.putShort(key, value)
    }

    override fun encodeString(value: String) {
        bundle.putString(key, value)
    }

    override fun encodeUnit() {
        bundle.putString(key, Unit.toString())
    }

    override fun encodeNotNullMark() {

    }
}
