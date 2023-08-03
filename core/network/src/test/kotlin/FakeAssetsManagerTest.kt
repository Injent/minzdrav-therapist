
import io.bloco.faker.Faker
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toKotlinInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import ru.minzdrav.therapist.core.model.CallStatus
import ru.minzdrav.therapist.core.model.TherapistCall
import ru.minzdrav.therapist.core.model.forms.ViewKind
import ru.minzdrav.therapist.core.model.forms.UiSchema
import ru.minzdrav.therapist.core.model.patient.Address
import ru.minzdrav.therapist.core.model.patient.ContactPoint
import ru.minzdrav.therapist.core.model.patient.HumanName
import ru.minzdrav.therapist.core.model.patient.Organization
import ru.minzdrav.therapist.core.model.patient.Patient
import ru.minzdrav.therapist.core.model.patient.Polis
import java.io.File
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.time.Duration.Companion.minutes

@RunWith(JUnit4::class)
class FakeAssetsManagerTest {
    private val json = Json { prettyPrint = true }
    private val sampledata = File("sampledata")
    private val faker = Faker("en")

    @Test
    fun generateCommonInspectionUiSchema() {
        val uiSchema = UiSchema(
            version = Int.MAX_VALUE,
            fields = listOf(
                ViewKind.TextField(
                    id = "complaints",
                    label = "Жалобы",
                    placeholder = "Напишите о жалобах пациента",
                    singleLine = false
                ),
                ViewKind.CheckBox(
                    id = "is_child",
                    false,
                    text = "Какая-то норма"
                )
            )
        )

        File(sampledata, "common_inspections.json").apply {
            writeText(json.encodeToString(UiSchema.serializer(), uiSchema))
        }
    }

    @Test
    fun generateTherapistCalls() {
        val calls = (0..10).map {
            generateCall()
        }

        File(sampledata, "therapist_calls.json").apply {
            writeText(json.encodeToString(calls))
        }
    }

    private fun generateCall(): TherapistCall {
        val addressProvider = faker.address
        val nameProvider = faker.name

        val address = Address(
            territory = addressProvider.city(),
            populatedArea = addressProvider.state(),
            street = addressProvider.streetAddress(),
            building = (1..30).random(),
            block = (1..10).random(),
            flatNumber = (1..100).random()
        )

        val patient = Patient(
            name = HumanName(
                firstName = nameProvider.firstName(),
                lastName = nameProvider.lastName(),
                middleName = nameProvider.nameWithMiddle().split(" ").last()
            ),
            birthDate = faker.date.birthday().toInstant().toKotlinInstant().toLocalDateTime(TimeZone.UTC).date,
            sex = Patient.Sex.values().random(),
            residenceAddress = address,
            currentAddress = address,
            contactNumber = faker.phoneNumber.cellPhone(),
            polis = Polis(Polis.Type.NEW, number = 1234567890123456),
            managingOrganization = Organization(
                identifier = Random.nextLong().toString(),
                active = true,
                name = faker.company.suffix(),
                alias = listOf(faker.company.bs()),
                telecom = listOf(ContactPoint(ContactPoint.System.PHONE, faker.phoneNumber.cellPhone())),
                address = listOf(),
                contact = Organization.Contact(
                    purpose = "Help",
                    name = HumanName(nameProvider.firstName(), nameProvider.lastName(), nameProvider.nameWithMiddle()),
                    telecom = emptyList(),
                    address = Address(
                        territory = addressProvider.city(),
                        populatedArea = addressProvider.state(),
                        street = addressProvider.streetAddress(),
                        building = addressProvider.buildingNumber().toInt(),
                        block = (1..10).random(),
                        flatNumber = (1..200).random()
                    )
                )
            )
        )

        return TherapistCall(
            id = Random.nextLong(),
            patient = patient,
            complaints = faker.lorem.words(23).joinToString(" "),
            status = CallStatus.values().random(),
            registrationDate = Clock.System.now().minus(Random.nextInt(1..60).minutes)
        )
    }
}